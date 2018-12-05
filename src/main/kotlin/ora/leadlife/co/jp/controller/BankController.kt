package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.AutomaticWithdrawalsForm
import ora.leadlife.co.jp.form.BankForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/bank")
class BankController {
    @Autowired
    lateinit var bankService: BankService

    @Autowired lateinit var accountService : AccountService

    @GetMapping
    fun index() = "bank/index"



    @GetMapping(path= arrayOf("/input"))
    fun inputView(model: Model,@AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount :String?):String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "bank/input"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        //if view is 1, then aboutMeViewAccount exists else none
        val list = mutableListOf<BankForm>()
        val bankForm = BankForm()
        val bankList = bankService.findByAccount(account)
        if(bankList.isNotEmpty()){
            bankList.forEach {
                val automaticWithdrawalslist = mutableListOf<AutomaticWithdrawalsForm>()
                it.automaticWithdrawals!!.forEach {
                    automaticWithdrawalslist.add(AutomaticWithdrawalsForm(expenseItem = it.expenseItem,withdrawalDate = it.withdrawalDate,memo = it.memo))
                }
                list.add(BankForm(name = it.name,branchName = it.branchName,depositType = it.depositType,bankAccountNumber = it.bankAccountNumber,holder = it.holder,webId = it.webId,bankUsage = it.bankUsage,automaticWithdrawalsWrapper = automaticWithdrawalslist))
            }
            bankForm.bankFormWrapper=list
            model.addAttribute("bankForm",bankForm)
        }else{
            model.addAttribute("bankForm",bankForm)
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path= arrayOf("/bankSave"))
    fun save(bankForm: BankForm,@AuthenticationPrincipal user:User): String {
        bankService.save(bankForm,user.lastUsedAccount!!)
        return "redirect:/bank/input"
    }
}