package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.GraveForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.GraveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/grave")
class GraveController {

    @Autowired
    lateinit var graveService: GraveService

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
        return "grave/funeral_about_grave"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(graveService.findByAccountId(account) != null ) {
            if(account.id!! == graveService.findByAccountId(account)!!.account.id!!) {
                val g = graveService.findByAccountId(account)!!
                val date = if(g.contractDate != null) { g.contractDate } else {null}
                val form = GraveForm(
                        graveId = g.id!!,
                        personWantInheritance = g.personWantInheritance!! , isPrepared = g.isPrepared!!,
                        name = g.name!!, postCode1 = g.postCode1!!,postCode2 = g.postCode2!! ,
                        address = g.address!!, contactAddress = g.contactAddress!!, graveUser = g.graveUser!!,
                        purchaseName = g.purchaseStoreName!!, religion = g.religionType!!,
                        management = g.managementStatus!!, remarks = g.memo!! , contractDate = date,
                        cinerationMethod = g.cinerationMethod!!)
                model.addAttribute("form", form)
            }else {
                model.addAttribute("form", GraveForm())
            }
        }else {
            model.addAttribute("form", GraveForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, graveForm: GraveForm, @AuthenticationPrincipal user: User): String {
        graveService.save(graveForm,user)
        return "redirect:/grave/add"
    }
}