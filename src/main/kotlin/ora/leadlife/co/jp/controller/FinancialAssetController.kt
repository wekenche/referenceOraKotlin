package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.FinancialAssetForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.FinancialAssetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/financialAsset")
class FinancialAssetController {

    @Autowired
    lateinit var financialAssetService : FinancialAssetService

    @Autowired lateinit var accountService: AccountService


    @GetMapping
    fun input(model: Model,  @AuthenticationPrincipal user: User, @RequestParam(required=false) aboutMeViewAccount :String?): String{
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "financialAsset/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(financialAssetService.findByAccount(account).isNotEmpty()) {
            val list : ArrayList<FinancialAssetForm> = ArrayList()
            val financial = FinancialAssetForm()
            financialAssetService.findByAccount(account).forEach {
                val financialAsset = FinancialAssetForm(name = it.name , holder = it.holder, company = it.company, symbolNo = it.symbolNo, contactAddress=it.contactAddress, memo=it.memo)
                list.add(financialAsset)
            }
            financial.financialAssetWrapper = list
            model.addAttribute("financialForm" , financial)
        } else {
            model.addAttribute("financialForm" , FinancialAssetForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun add(model:Model, financialAssetForm: FinancialAssetForm, @AuthenticationPrincipal user:User):String{
        if(financialAssetForm.financialAssetWrapper!!.isNotEmpty()) {
        financialAssetService.save(financialAssetForm.financialAssetWrapper!!,user)
        model.addAttribute("financialAssetForm" , FinancialAssetForm())
        return "redirect:/financialAsset"
        } else {
            println("it's empty")
        }
        return "redirect:/financialAsset"

    }
}