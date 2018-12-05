package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.PaymentForm
import ora.leadlife.co.jp.service.PaymentService
import ora.leadlife.co.jp.service.SettingService
import ora.leadlife.co.jp.util.PriceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController {

    @Autowired
    lateinit var paymentService : PaymentService

    @Autowired
    lateinit var settingService: SettingService

    @PostMapping("/payment/save")
    fun savePayment(@RequestBody paymentForm: PaymentForm): MutableMap<String, String>? {
        calcAmountFrom(paymentForm)
        return paymentService.saveFromPaymentForm(paymentForm)
    }

    private fun calcAmountFrom(paymentForm: PaymentForm) {
        val setting = settingService.findFirst()
        paymentForm.amount = if (paymentForm.appleProductId!!.startsWith("NORMAL_"))
            PriceUtil.includingTaxFloor(setting.normalFee, setting.tax)
        else
            PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax)

    }
    @GetMapping("/payment")
    fun saveAppleReceipt() {
        paymentService.checkApple()
    }

}