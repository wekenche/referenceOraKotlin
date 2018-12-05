package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.CashForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.CashService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
@RequestMapping("/cash")
class CashController {

    @Autowired
    lateinit var cashService: CashService

    @Autowired lateinit var accountService: AccountService


    @GetMapping
    fun input(model: Model, cashForm : CashForm, @AuthenticationPrincipal user: User, @RequestParam(required=false) aboutMeViewAccount :String?): String{
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "cash/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(cashService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<CashForm> = ArrayList()
            val cash = CashForm()
            cashService.findByAccount(account).forEach {
                val cashF = CashForm(amount = it.amount , storageLocation = it.storageLocation, message = it.message)
                list.add(cashF)
            }
            cash.cashFormWrapper = list
            model.addAttribute("cashForm" , cash)
        } else {
            model.addAttribute("cashForm" , CashForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun add(model: Model, cashForm : CashForm, @AuthenticationPrincipal user:User):String{
        if(cashForm.cashFormWrapper!!.isNotEmpty()) {
            cashService.save(cashForm.cashFormWrapper!!,user)
            model.addAttribute("cashForm" , CashForm())
            return "redirect:/cash" } else {
            println("it's empty")
        }
        return "redirect:/cash"
    }







}