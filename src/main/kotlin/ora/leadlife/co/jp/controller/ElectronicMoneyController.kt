package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.ElectronicMoneyForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.ElectronicMoneyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
@RequestMapping("/electronicMoney")
class ElectronicMoneyController {

    @Autowired
    lateinit var electronicMoneyService: ElectronicMoneyService

    @Autowired lateinit var accountService: AccountService


    @GetMapping
    fun inputView(model: Model, @AuthenticationPrincipal user: User, @RequestParam(required=false) aboutMeViewAccount :String?): String{
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account, model, 1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "electronicMoney/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(electronicMoneyService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<ElectronicMoneyForm> = ArrayList()
            val elec = ElectronicMoneyForm()
            electronicMoneyService.findByAccount(account).forEach {
                val elecMon = ElectronicMoneyForm(name = it.name , no = it.no, certificateCompany = it.certificateCompany)
                list.add(elecMon)
            }
            elec.electronicMoneyWrapper = list
            model.addAttribute("electForm" , elec)
        } else {
            model.addAttribute("electForm" , ElectronicMoneyForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun add(model: Model, electronicMoneyForm : ElectronicMoneyForm, @AuthenticationPrincipal user:User):String{
        if(electronicMoneyForm.electronicMoneyWrapper!!.isNotEmpty()) {
        electronicMoneyService.save(electronicMoneyForm.electronicMoneyWrapper!!,user)
            return "redirect:/electronicMoney"
        } else {
            println("it's empty")
        }
        return "redirect:/electronicMoney"
    }



}