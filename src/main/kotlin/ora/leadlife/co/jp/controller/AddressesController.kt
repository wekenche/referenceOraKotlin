package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.AddressForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/addresses")
class AddressesController {

    @Autowired lateinit var addressService: AddressService
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
        return "addresses/property_about_pastAddress"
    }

    fun setPage(account: Account,model: Model,view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(addressService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<AddressForm> = ArrayList()
            val addressForm = AddressForm()
            addressService.findByAccount(account).forEach {
                val address = AddressForm(addressId = it.id!!, address = it.name!! , postCode1 = it.postCode1!! , postCode2 = it.postCode2!!)
                list.add(address)
            }
            addressForm.addWrapper = list
            model.addAttribute("form" , addressForm)
        } else {
            model.addAttribute("form" , AddressForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, addressForm: AddressForm, @AuthenticationPrincipal user: User): String {
        addressService.save(addressForm,user)
        return "redirect:/addresses/add"
    }
}