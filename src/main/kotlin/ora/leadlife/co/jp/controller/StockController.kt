package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.StockForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Stock
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/stock")
class StockController {

    @Autowired
    lateinit var stockService: StockService

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
        return "stock/property_about_stock"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        if(stockService.findByAccount(account).isNotEmpty()) {
            val stock = StockForm()
            val list : ArrayList<StockForm> = ArrayList()
            stockService.findByAccount(account).forEach {
                val s = StockForm(company = it.certificateCompany , brand = it.brand,number = it.certificateNo , quantity = it.quantity,
                        remarks = it.memo, isCustomManagement = it.isCustomManagement)
                list.add(s)
            }
            stock.stockWrapper = list
            model.addAttribute("form", stock)
        } else {
            model.addAttribute("form" , StockForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, stockForm: StockForm, @AuthenticationPrincipal user: User): String {
        stockService.save(stockForm.stockWrapper!!,user)
        return "redirect:/stock/add"
    }
}