package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.DigitalForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.PhoneCompanyPasswords
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.DigitalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/digital")
class DigitalController {
    @Autowired
    lateinit var digitalService : DigitalService

    @Autowired lateinit var accountService: AccountService

    @GetMapping
    fun inputView(model: Model,@AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?):String{
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "digital/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(digitalService.findOneByAccount(account)!=null){
            model.addAttribute("digitalForm",digitalService.findAllByAccount(account))
        }else{
            model.addAttribute("digitalForm",DigitalForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }


    @PostMapping(path= arrayOf("/digitalSave"))
    fun save(digitalForm: DigitalForm,@AuthenticationPrincipal user: User):String{
        digitalService.save(digitalForm,user.lastUsedAccount!!)
        return "redirect:/digital"
    }
}