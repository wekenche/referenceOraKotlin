package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.PensionsForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.PensionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/pensions")
class PensionsController {

    @Autowired
    lateinit var pensionsService : PensionsService

    @Autowired lateinit var accountService: AccountService


    @GetMapping
    fun inputView(model: Model,  @AuthenticationPrincipal user: User, @RequestParam(required=false) aboutMeViewAccount :String?): String{
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account, model, 1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "pensions/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(pensionsService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<PensionsForm> = ArrayList()
            val pen = PensionsForm()
            pensionsService.findByAccount(account).forEach {
                val penForm = PensionsForm(pensionNo = it.pensionNo!! , pensionType = it.pensionType!!, privatePension = it.privatePension!!,contactAddress = it.contactAddress!!,memo = it.memo!!)
                list.add(penForm)
            }
            pen.pensionsWrapper = list
            model.addAttribute("penForm" , pen)
        } else {
            model.addAttribute("penForm" , PensionsForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun add(model:Model,pensionsForm: PensionsForm, @AuthenticationPrincipal user:User):String{
        if(pensionsForm.pensionsWrapper!!.isNotEmpty()) {
        pensionsService.save(pensionsForm.pensionsWrapper!!,user)
            return "redirect:/pensions"
        } else {
            println("it's empty")
        }
        return "redirect:/pensions"

    }

}