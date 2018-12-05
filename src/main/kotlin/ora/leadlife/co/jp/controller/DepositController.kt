package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.DepositForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.DepositService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/deposit")
class DepositController {
    @Autowired
    lateinit var depositService: DepositService

    @Autowired
    lateinit var accountService: AccountService

    @GetMapping
    fun addView(model: Model, deposit: DepositForm,@AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?): String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "deposit/property_about_otherHeritage"
    }

    fun setPage(account: Account,model: Model,view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        val deposit = depositService.findByAccount(account)
        if(deposit.isNotEmpty()){
            val depositForm = DepositForm()
            val depositList:MutableList<DepositForm> = mutableListOf()
            deposit.forEach {
                val list = DepositForm(insuranceCompany = it.category,symbolNo = it.symbolNo,securityNo = it.certificateNo,memo = it.memo)
                depositList.add(list)
            }
            depositForm.depositWrapper = depositList
            model.addAttribute("depositForm",depositForm)
        }else{
            model.addAttribute("depositForm",DepositForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/depositSave"))
    fun save(deposit: DepositForm, @AuthenticationPrincipal user: User): String {
        val account : Account = user.lastUsedAccount!!
        depositService.save(deposit,account)
        return "redirect:/deposit"
    }
}