package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.TreasuresForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.TreasuresService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/treasures")
class TreasureController {
    @Autowired
    lateinit var treasuresService: TreasuresService

    @Autowired lateinit var accountService: AccountService


    @GetMapping
    fun treasuresIndexView(model: Model, @AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?): String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "property/property_about_collection"
    }

    fun setPage(account: Account,model: Model,view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        val treasureList = treasuresService.findByAccount(account)
        if(treasureList.isNotEmpty()){
            val treasureForm = TreasuresForm()
            val list = mutableListOf<TreasuresForm>()
            treasureList.forEach {
                list.add(TreasuresForm(name = it.name!!,storingPlace = it.storingPlace!!,processingMethod = it.processingMethod!!,remarks = it.memo!!))
            }
            treasureForm.treasureWrapper = list
            model.addAttribute("treasuresForm",treasureForm)
        }else{
            model.addAttribute("treasuresForm",TreasuresForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/treasuresSave"))
    fun treasuresInput(treasuresForm: TreasuresForm, @AuthenticationPrincipal user: User): ModelAndView {
        val account : Account = user.lastUsedAccount!!
        treasuresService.save(treasuresForm, account)
        return ModelAndView("redirect:/treasures")
    }
}