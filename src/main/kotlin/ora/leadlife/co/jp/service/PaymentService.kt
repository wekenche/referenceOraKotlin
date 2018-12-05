package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.dto.AppleJsonDto
import ora.leadlife.co.jp.form.PaymentForm
import ora.leadlife.co.jp.model.*
import ora.leadlife.co.jp.repository.PaymentRepository
import ora.leadlife.co.jp.repository.SettingRepository
import ora.leadlife.co.jp.repository.ShopPayHistoryPaymentRepository
import ora.leadlife.co.jp.repository.ShopPayHistoryRepository
import ora.leadlife.co.jp.util.PriceUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*


@Service
class PaymentService {
    val logger: Logger = LoggerFactory.getLogger(PaidRegistrationService::class.java)

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    @Autowired
    lateinit var shopPayHistoryRepository: ShopPayHistoryRepository

    @Autowired
    lateinit var shopPayHistoryPaymentRepository: ShopPayHistoryPaymentRepository

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var settingRepository: SettingRepository

    @Autowired
    lateinit var paidRegistrationService: PaidRegistrationService

    /**
     * 1. save payment
     * 2. add or get shop_pay_histories. set var shopPayHistory
     * if (payment#account#user#shop != null){
     *  if((shop_pay_histories where payed =false and shop=payment#account#user#shop) != null){ # get
     *  else # add
     *      shop = payment#account#user#shop
     *      amount = 0
     *      pay_date = null
     *      planed_amount = 0
     *      payed = false
     *  }
     * }
     * 3. set planed_amount. and add shop_pay_history_payments
     *  if(payment#account#user#shop != null && payment#account#user#shop#parent != null ){
     *      do 2(add or get shop_pay_histories)
     *    shopPayHistory#planed_amount=payment#amount * back_parent_percent / 0.01
     *    add shop_pay_history_payments(amount = payment#amount * back_parent_percent / 0.01)
     *  }
     *  if(payment#account#user#shop != null){
     *    shopPayHistory#planed_amount=payment#amount * back_parent / 0.01
     *    add shop_pay_history_payments(amount = payment#amount * back_parent / 0.01)
     *   }
     */
    fun save(paymentForm: PaymentForm, account: Account) {
        val payment = paymentRepository.save(
                Payment(
                        paydate = paymentForm.paydate,
                        amount = paymentForm.amount,
                        account = account,
                        accountId = account.id!!,
                        appleReceipt = paymentForm.appleReceipt,
                        appleTransactionId = paymentForm.appleTransactionId,
                        appleProductId = paymentForm.appleProductId
                )
        )

        //get or set shopPayHistory

        saveFromPayment(payment)


    }

    fun saveFromPayment(payment: Payment) {
        if (payment.account!!.user.shop != null) {
            var shopPayHistory = if (shopPayHistoryRepository.findByShopAndPayed(payment.account!!.user.shop!!, false) != null) {
                shopPayHistoryRepository.findByShopAndPayed(payment.account!!.user.shop!!, false)
            } else {
                shopPayHistoryRepository.save(
                        ShopPayHistory(shop = payment.account!!.user.shop!!
                                , amount = 0, planedAmount = 0
                                , payed = false))
            }
            //parent and child
            if (payment.account!!.user.shop != null && payment.account!!.user.shop!!.parent != null) {
                shopPayHistory!!.planedAmount += (payment.amount * (payment.account!!.user.shop!!.backParentPercent * 0.01)).toInt()
                shopPayHistory = shopPayHistoryRepository.save(shopPayHistory)
                shopPayHistoryPaymentRepository.save(
                        ShopPayHistoryPayment(amount = (payment.amount * (payment.account!!.user.shop!!.backParentPercent * 0.01)).toInt()
                                , payment = payment
                                , shopPayHistory = shopPayHistory!!))
            }

            //parent
            if (payment.account!!.user.shop != null) {
                shopPayHistory!!.planedAmount += (payment.amount * (payment.account!!.user.shop!!.backPercent * 0.01)).toInt()
                shopPayHistoryRepository.save(shopPayHistory)
                shopPayHistoryPaymentRepository.save(
                        ShopPayHistoryPayment(amount = (payment.amount * (payment.account!!.user.shop!!.backPercent * 0.01)).toInt()
                                , payment = payment
                                , shopPayHistory = shopPayHistory!!))
            }
        }
    }

    fun makeShopPayHistoryById(paymentId: Long) {
        val payment = paymentRepository.findOne(paymentId)
        saveFromPayment(payment)
    }

    fun findByAppleTransactionId(transactionId: Long): Payment? {
        return paymentRepository.findFirstByAppleTransactionId(transactionId)
    }

    fun checkApple() {
        val setting = settingRepository.findAll().first()
        val normalPrice = PriceUtil.includingTaxFloor(setting.normalFee, setting.tax)
        val discountedFee = PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax)
        paymentRepository.findLastOfApple().forEach {
            checkDataFromApple(it, normalPrice, discountedFee)
        }
    }

    private fun checkDataFromApple(it: Payment, normalPrice: Int, discountedFee: Int, isSandBox: Boolean = false) {
        var uri = URI("https://buy.itunes.apple.com/verifyReceipt")
        if (isSandBox)
            uri = URI("https://sandbox.itunes.apple.com/verifyReceipt")
        val client = RestTemplate(SimpleClientHttpRequestFactory())
        val mappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
        mappingJackson2HttpMessageConverter.supportedMediaTypes = Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM)
        client.messageConverters.add(mappingJackson2HttpMessageConverter)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON_UTF8
        val requestJson = "{\"receipt-data\" : \"${it.appleReceipt}\",\"password\": \"e330cc87200d44e48c02e3749f85db19\"}"
        val requestEntity = RequestEntity<Any>(requestJson, headers, HttpMethod.POST, uri)
        val responseEntity = client.exchange(requestEntity, AppleJsonDto::class.java)
        if (responseEntity.body.status == 21007)
            return checkDataFromApple(it, normalPrice, discountedFee, true)
        val lastReceipt = responseEntity.body.lastLatestReceiptInfo()
        logger.info("${lastReceipt.transaction_id} : ${it.appleTransactionId}")
        if (lastReceipt.transaction_id != it.appleTransactionId) {
            saveFromPaymentForm(PaymentForm(
                    appleTransactionId = lastReceipt.transaction_id,
                    amount = if (lastReceipt.product_id.startsWith("NORMAL_")) normalPrice else discountedFee,
                    paydate = lastReceipt.getPurchaseDate(),
                    accountId = it.accountId,
                    appleReceipt = responseEntity.body.latest_receipt,
                    appleProductId = lastReceipt.product_id
            ))
        }
        val current = System.currentTimeMillis()
        logger.info("${lastReceipt.expires_date_ms} : $current ${lastReceipt.expires_date_ms - current}")
        if (lastReceipt.expires_date_ms < System.currentTimeMillis()) {
            logger.info("${it.accountId} to free")
            val account = it.account
            if (account != null) {
                paidRegistrationService.changeFreeAccountAndSendMail(account.user, account)
            }
        }
    }

    fun saveFromPaymentForm(paymentForm: PaymentForm): MutableMap<String, String>? {
        if (paymentForm.appleTransactionId != null && findByAppleTransactionId(paymentForm.appleTransactionId!!) != null) {
            return Collections.singletonMap("result", "ok")
        }
        val account = accountService.findById(paymentForm.accountId!!)
        logger.info(paymentForm.toString())
        save(paymentForm, account)
        account.isPremium = true
        account.premiumPayLastDate = Date()
        accountService.save(account)
        return Collections.singletonMap("result", "ok")
    }

    fun shopPay(shopPayHistoryId: Long) {
        val shopPayHistory = shopPayHistoryRepository.findOne(shopPayHistoryId)
        shopPayHistory.payed = true
        shopPayHistory.payDate = Date()
        shopPayHistory.amount = shopPayHistory.planedAmount
        shopPayHistoryRepository.save(shopPayHistory)
    }

    fun unpaid(shop: Shop): ShopPayHistory? {
        return shopPayHistoryRepository.findByShopAndPayed(shop, false)
    }
}
