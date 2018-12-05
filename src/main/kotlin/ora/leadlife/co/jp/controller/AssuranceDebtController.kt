package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.AssuranceDebtForm
import ora.leadlife.co.jp.form.ElectronicMoneyForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.AssuranceDebt
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.AssuranceDebtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/assuranceDebt")
class AssuranceDebtController {

    @Autowired
    lateinit var assuranceDebtService: AssuranceDebtService

    @Autowired lateinit var accountService: AccountService



    @GetMapping
    fun input(model: Model, electronicMoneyForm : ElectronicMoneyForm, @AuthenticationPrincipal user: User, @RequestParam(required=false) aboutMeViewAccount :String?): String{
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "assuranceDebt/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        if(assuranceDebtService.findByAccount(account).isNotEmpty()) {
            var list : ArrayList<AssuranceDebtForm> = ArrayList()
            var assurance = AssuranceDebtForm()
            assuranceDebtService.findByAccount(account).forEach {
                var assuranceDebt = AssuranceDebtForm(assuranceDate = it.assuranceDate,
                        assurancePrice = it.assurancePrice,
                        assuranceTarget = it.assuranceTarget ,
                        assuranceTargetContactAddress = it.assuranceTargetContactAddress,
                        creditor = it.creditor,
                        creditorContractAddress = it.creditorContractAddress)
                list.add(assuranceDebt)
            }
            assurance.assuranceDebtWrapper = list
            model.addAttribute("assuranceForm" , assurance)
        } else {
            model.addAttribute("assuranceForm" , AssuranceDebtForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun add(model: Model, assuranceDebtForm : AssuranceDebtForm, @AuthenticationPrincipal user:User):String{
        if(assuranceDebtForm.assuranceDebtWrapper!= null) {
            assuranceDebtService.save(assuranceDebtForm.assuranceDebtWrapper!!,user)
            model.addAttribute("assuranceDebtForm" , AssuranceDebtForm())
            return "redirect:/assuranceDebt"
        } else {
            println("it's empty")
        }
        return "redirect:/assuranceDebt"


    }
}