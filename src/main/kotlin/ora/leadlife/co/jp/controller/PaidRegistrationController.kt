package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.PaidRegistrationForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.*
import ora.leadlife.co.jp.util.PriceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/paidRegistration")
class PaidRegistrationController {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var paidRegistrationService: PaidRegistrationService

    @Autowired
    lateinit var shopService: ShopService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var settingService: SettingService

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User,@RequestHeader(value = "User-Agent") header : String): String {
        val setting = settingService.findFirst()
        if (user.shop == null) {
            model.addAttribute("amount", setting.normalFee)
            model.addAttribute("settingTax", PriceUtil.includingTaxFloor(setting.normalFee, setting.tax))
            model.addAttribute("discountFee", setting.discountedFee)
            model.addAttribute("discountFeeTax", PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax))
            model.addAttribute("nullShop", true)
        } else {
            model.addAttribute("amount", setting.discountedFee)
            model.addAttribute("settingTax", PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax))
            model.addAttribute("discountFee", setting.discountedFee)
            model.addAttribute("discountFeeTax", PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax))
            model.addAttribute("nullShop", false)
        }
        model.addAttribute("os", header.contains("ora_ios"))
        val nameOfApplePay = userService.getNameOfApplePay(user.lastUsedAccount!!.id.toString())
        model.addAttribute("nameOfApplePayCounter", nameOfApplePay.second)
        return "paidRegistration/index"
    }

    @GetMapping(path = arrayOf("/checkShop/{shop}"))
    @ResponseBody
    fun checkShop(model: Model, @PathVariable shop: String): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val setting = settingService.findFirst()
        if (shopService.findByCode(shop) != null) {
            map.put("flag", true)
            map.put("amount", setting.discountedFee)
            map.put("settingTax", PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax))
        } else {
            map.put("flag", false)
            map.put("amount", setting.normalFee)
            map.put("settingTax", PriceUtil.includingTaxFloor(setting.normalFee, setting.tax))
        }
        return map
    }

    @GetMapping(path = arrayOf("/aboutPay"))
    fun aboutPay(model: Model, @AuthenticationPrincipal user: User, @RequestHeader(value = "User-Agent") header : String): String {
        val setting = settingService.findFirst()
        val tax = if (user.shop == null) {
            PriceUtil.includingTaxFloor(setting.normalFee, setting.tax)
        } else {
            PriceUtil.includingTaxFloor(setting.discountedFee, setting.tax)
        }
        return when {
            (header.contains("ora_ios")) -> {
                model.addAttribute("settingTax", tax)
                "paidRegistration/aboutPayIOS"
            } else -> {
                model.addAttribute("settingTax", tax)
                "paidRegistration/aboutPay"
            }
        }
    }

    @GetMapping("/input")
    fun input(model: Model, paidRegistrationForm: PaidRegistrationForm, @AuthenticationPrincipal user: User,
              @RequestParam(required = false, value = "shopName") shopCode: String?): String {
        if (!shopCode.isNullOrEmpty()) {
            if (shopService.findByCode(shopCode!!) != null) {
                val shop = shopService.findByCode(shopCode)
                userService.updateUserShop(shop!!.id!!, user.id!!)
            }
        }
        if (paidRegistrationForm.amount == 0)
            paidRegistrationForm.amount = paidRegistrationService.getAmountIncludingTax(user = user).third
        model.addAttribute(paidRegistrationForm)
        return "paidRegistration/input"
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, paidRegistrationForm: PaidRegistrationForm): String {
        model.addAttribute(paidRegistrationForm)
        return "paidRegistration/confirm"
    }

    @PostMapping(path = arrayOf("/done"))
    fun done(model: Model, @AuthenticationPrincipal user: User, paidRegistrationForm: PaidRegistrationForm): String {
        val responseDto = paidRegistrationService.pay(user, paidRegistrationForm)
        return if (responseDto.mstatus == "success") {
            "paidRegistration/done"
        } else {
            model.addAttribute("error", responseDto.vResultCode)
            "paidRegistration/input"
        }
    }

    @GetMapping("/done")
    fun justDone(): String {
        return "paidRegistration/done"

    }
}