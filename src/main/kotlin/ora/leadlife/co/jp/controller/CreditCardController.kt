package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.CreditCardForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.CreditCardService
import ora.leadlife.co.jp.service.IndividualNoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/creditCard")
class CreditCardController {

    @Autowired
    lateinit var creditCardService: CreditCardService

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
        return "creditCard/property_about_credit"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(creditCardService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<CreditCardForm> = ArrayList()
            val creditcard = CreditCardForm()
            creditCardService.findByAccount(account).forEach {
                val cc = CreditCardForm(creditCardId = it.id!! , creditCardName = it.name, creditCardBrand = it.brand, creditCardNumber = it.cardNo, contactInfo = it.contactAddress, web = it.webId, remarks = it.memo)
                list.add(cc)
            }
            creditcard.creditCardWrapper = list
            model.addAttribute("form" , creditcard)
        } else {
            model.addAttribute("form" , CreditCardForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, creditCardForm: CreditCardForm, @AuthenticationPrincipal user: User): String {
        creditCardService.save(creditCardForm,user)
        return "redirect:/creditCard/add"
    }
}