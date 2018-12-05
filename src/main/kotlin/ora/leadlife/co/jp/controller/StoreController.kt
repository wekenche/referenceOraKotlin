package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.StoreForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Store
import ora.leadlife.co.jp.service.StoreService
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/store")
class StoreController {

    @Autowired lateinit var storeService: StoreService
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
        return "store/property_about_trunkroom"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(storeService.findByAccount(account).isNotEmpty()) {
            val store = StoreForm()
            val list :ArrayList<StoreForm> = ArrayList()
            storeService.findByAccount(account).forEach {
                val s = StoreForm(companyName = it.contractCompany, contact = it.contractAddress , postCode1 = it.postCode1,
                        postCode2 = it.postCode2, address = it.contractAddress, memo = it.memo)
                list.add(s)
            }
            store.storeWrapper = list
            model.addAttribute("form" , store)
        } else {
            model.addAttribute("form" , StoreForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model,storeForm: StoreForm, @AuthenticationPrincipal user: User): String {
        storeService.save(storeForm,user)
        return "redirect:/store/add"
    }
}