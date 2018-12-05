package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.CertificateAccountForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.CertificateAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/certificateAccount")
class CertificateAccountController {

    @Autowired lateinit var certificateAccountService: CertificateAccountService
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
        return "certificateAccount/property_about_securitiesAccount"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        if(certificateAccountService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<CertificateAccountForm> = ArrayList()
            val cert = CertificateAccountForm()
            certificateAccountService.findByAccount(account).forEach {
                val lcert = CertificateAccountForm(caId = it.id!! , caName = it.name!!, bankAccountNumber = it.bankAccountNumber!!, holder = it.holder!!, webId = it.webId!!, contactAddress = it.contactAddress!!, remarks = it.memo!!)
                list.add(lcert)
            }
            cert.caWrapper = list
            model.addAttribute("form" , cert)
        } else {
            model.addAttribute("form" , CertificateAccountForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, certificateAccountForm: CertificateAccountForm, @AuthenticationPrincipal user: User): String {

        if(certificateAccountForm.caWrapper!!.isNotEmpty()) {
            certificateAccountService.save(certificateAccountForm,user)
            return "redirect:/certificateAccount/add"
        } else {
            println("it's empty")
        }

        return "redirect:/certificateAccount/add"
    }
}