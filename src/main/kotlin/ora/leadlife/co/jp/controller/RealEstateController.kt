package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.RealEstateForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.RealEstateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.lang.model.type.ArrayType


@Controller
@RequestMapping("/realEstate")
class RealEstateController {

    @Autowired lateinit var realEstateService: RealEstateService
    @Autowired lateinit var accountService: AccountService


    @GetMapping(path = arrayOf("/add"))
    fun add(model: Model, @AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?): String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "realEstate/property_about_realEstate"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(realEstateService.findByAccount(account).isNotEmpty()) {
            val realEstate = RealEstateForm()
            val list : ArrayList<RealEstateForm> = ArrayList()
            realEstateService.findByAccount(account).forEach {
                val re = RealEstateForm(address = it.address!! , parcelNumber = it.parcelNumber!! , equity = it.equity!! ,
                        landCategory = it.landCategory!!, memo = it.memo!!)
                list.add(re)
            }
            realEstate.estateWrapper = list
            model.addAttribute("form",realEstate)
        } else {
            model.addAttribute("form" , RealEstateForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, realEstateForm: RealEstateForm, @AuthenticationPrincipal user: User): String {
        realEstateService.save(realEstateForm,user)
        return "redirect:/realEstate/add"
    }
}