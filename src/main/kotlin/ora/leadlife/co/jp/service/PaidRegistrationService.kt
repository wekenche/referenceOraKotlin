package ora.leadlife.co.jp.service

import jp.veritrans.tercerog.mdk.TransactionFactory
import jp.veritrans.tercerog.mdk.dto.*
import me.mattak.moment.Moment
import ora.leadlife.co.jp.form.PaidRegistrationForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Payment
import ora.leadlife.co.jp.model.Setting
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.PaymentRepository
import ora.leadlife.co.jp.repository.SettingRepository
import ora.leadlife.co.jp.util.PriceUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class PaidRegistrationService {

    val logger: Logger = LoggerFactory.getLogger(PaidRegistrationService::class.java)

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    @Autowired
    lateinit var settingRepository: SettingRepository

    @Autowired
    lateinit var settingService: SettingService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var sender: MailSender

    @Autowired
    lateinit var paymentService: PaymentService

    @Transactional
    fun pay(user: User, paidRegistrationForm: PaidRegistrationForm): CardAuthorizeResponseDto {
        val (setting, account, amount) = getAmountIncludingTax(user)
        var payment = Payment(account = account, amount = amount, accountId = account.id!!)
        payment = paymentRepository.save(payment)
        val requestDto = CardAuthorizeRequestDto()
        requestDto.orderId = Moment().format("yyyyMMdd") + "-" + payment.id.toString()
        requestDto.amount = amount.toString()
        requestDto.token = paidRegistrationForm.token
        requestDto.withCapture = "true"

        val transaction = TransactionFactory.getInstance(requestDto)
        val responseDto = transaction.execute() as CardAuthorizeResponseDto
        if (responseDto.mstatus == "success") {
            account.isPremium = true
            account.premiumPayLastDate = Date()
            accountService.save(account)
        } else {
            logger.error("${account.id} is failed: ${responseDto.vResultCode} : ${responseDto.merrMsg} : order id = ${requestDto.orderId}")
        }
        payment.orderId = responseDto.orderId
        payment.mstatus = responseDto.mstatus
        payment.vResultCode = responseDto.vResultCode
        paymentRepository.save(payment)
        return responseDto
    }

    @Transactional
    fun rePay(account: Account): CardReAuthorizeResponseDto? {
        val user = account.user
        val (setting, tempAccount, amount) = getAmountIncludingTax(user)
        val lastPayment = paymentRepository.findFirstByAccountEqualsOrderByIdDesc(account)
        if (lastPayment == null || lastPayment.mstatus != "success" || lastPayment.orderId == null) {
            changeFreeAccountAndSendMail(user, account)
            return null
        }

        var payment = Payment(account = account, amount = amount, accountId = account.id!!)
        payment = paymentRepository.save(payment)
        val requestDto = CardReAuthorizeRequestDto()
        requestDto.orderId = Moment().format("yyyyMMdd") + "-" + payment.id.toString()
        requestDto.originalOrderId = lastPayment.orderId
        requestDto.amount = amount.toString()
        requestDto.withCapture = "true"

        val transaction = TransactionFactory.getInstance(requestDto)
        val responseDto = transaction.execute() as CardReAuthorizeResponseDto
        if (responseDto.mstatus == "success") {
            account.isPremium = true
            account.premiumPayLastDate = Date()
            accountService.save(account)
        } else {
            changeFreeAccountAndSendMail(user, account)
            logger.error("${account.id} is failed rePay: ${responseDto.vResultCode} : ${responseDto.merrMsg} : order id = ${requestDto.orderId}")
        }
        payment.orderId = responseDto.orderId
        payment.mstatus = responseDto.mstatus
        payment.vResultCode = responseDto.vResultCode
        paymentRepository.save(payment)
        paymentService.saveFromPayment(payment)

        return responseDto
    }

    fun changeFreeAccountAndSendMail(user: User, account: Account) {
        account.isPremium = false
        accountService.save(account)

        val msg = SimpleMailMessage()
        msg.from = settingService.getSystem().email
        msg.setTo(user.email)
        msg.subject = "生前整理 有料プラン決済失敗のお知らせ"
        msg.text = "生前整理アプリをご利用頂き誠にありがとうございます。\n" +
                "有料プランの決済に失敗しました。\n" +
                "現在、無料プランとさせて頂いておりますので、アプリから再度有料プランに変更頂きますようお願い申し上げます。\n" +
                "来月末までに、有料プランに変更頂けない場合は、有料プランのみで使用できるデータが削除されますことをご了承願います。"
        logger.info(msg.text)
        this.sender.send(msg)
    }

    fun getAmountIncludingTax(user: User): Triple<Setting, Account, Int> {
        val setting = settingRepository.findAll().first()
        val account = user.lastUsedAccount!!
        val amount = if (user.shop != null) setting.discountedFee else setting.normalFee
        val includingAmount = PriceUtil.includingTaxFloor(amount, setting.tax)
        return Triple(setting, account, includingAmount)
    }


    @Transactional
    fun withdraw(account: Account): RecurringDeleteResponseDto {
        val reqDto = RecurringDeleteRequestDto()
        reqDto.accountId = account.id.toString()
        reqDto.endDate = Moment().format("yyyyMMdd")
        reqDto.groupId = settingRepository.findAll().first().veritransGroupId
        val tran = TransactionFactory.getInstance(reqDto)
        val resDto = tran.execute() as RecurringDeleteResponseDto
        if (resDto.mstatus == "success") {
            account.isPremium = false
            accountService.save(account)
        } else {
            logger.error("${account.id} is failed: ${resDto.vResultCode} : ${resDto.merrMsg} ")
        }
        return resDto
    }
}