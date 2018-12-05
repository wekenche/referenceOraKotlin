package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.LendMoneyForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.LendMoneyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/lendMoney")
class LendMoneyController {

    @Autowired lateinit var lendMoneyService: LendMoneyService
    @Autowired lateinit var accountService: AccountService

    @GetMapping(path = arrayOf("/add"))
    fun addView(model: Model, @AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?): String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "lendMoney/property_about_moneyLending"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(lendMoneyService.findByAccount(account).isNotEmpty()) {
            val lendMoney = LendMoneyForm()
            val list : ArrayList<LendMoneyForm> = ArrayList()
            lendMoneyService.findByAccount(account).forEach {
                val lm = LendMoneyForm(name = it.name, tel = it.tel, postCode1 = it.postCode1, postCode2 = it.postCode2, address = it.address,
                        lendDate = it.lendDate, amount = it.amount, existDeed = it.existsDeed, storingPlace = it.storingPlace, balance = it.balance,
                        remarks = it.memo)
                list.add(lm)
            }
            lendMoney.lmWrapper = list
            model.addAttribute("form", lendMoney)
        } else {
            model.addAttribute("form" , LendMoneyForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, lendMoneyForm: LendMoneyForm, @AuthenticationPrincipal user: User): String {
        lendMoneyService.save(lendMoneyForm,user)
        return "redirect:/lendMoney/add"
    }
}