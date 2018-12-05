package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.CollateraliseForm
import ora.leadlife.co.jp.form.LoanForm
import ora.leadlife.co.jp.model.Loan
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.CollateraliseService
import ora.leadlife.co.jp.service.LoanService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@Controller

@RequestMapping("/property/loans")
class LoanController {
    @Autowired
    lateinit var loanService: LoanService

    @Autowired
    lateinit var collateraliseService: CollateraliseService

    @Autowired
    lateinit var  accountService: AccountService

    @GetMapping(path = arrayOf("/input"))
    fun input(model: Model,
              @AuthenticationPrincipal user: User,
              @RequestParam(required = false) aboutMeViewAccount :String?
    ): String {
        var account: Account = user.lastUsedAccount!!

        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        }
        else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account,model,1)
        }

        val loans = loanService.findByAccount(account)
        val loanForm = LoanForm()

        if(loans.isNotEmpty()) {
            val loanList : ArrayList<LoanForm> = ArrayList()

            loans.forEach {
                val collaterals = collateraliseService.findByLoan(it)

                val colList : ArrayList<CollateraliseForm> = ArrayList()
                collaterals.forEach {
                    colList.add(
                            CollateraliseForm(
                                    collateralId = it.id!!,
                                    name = it.name
                            )
                    )
                }

                loanList.add(
                        LoanForm(
                                loanId = it.id!!,
                                target = it.loanTarget,
                                tel = it.tel,
                                postCode1 = it.postCode1,
                                postCode2 = it.postCode2,
                                address = it.address,
                                dateY = it.loanDate.year.toString(),
                                dateM = it.loanDate.month.toString(),
                                dateD = it.loanDate.day.toString(),
                                amount = it.loanAmount,
                                refundMethod = it.refundMethod,
                                balance = it.loanBalance,
                                purpose = it.loanPurpose,

                                collateraliseFormWrapper = colList

                        )
                )
            }

            loanForm.loanFormWrapper = loanList
        }
        model.addAttribute("loanForm",loanForm)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)

        return "property/loans"
    }

    fun setPage(account: Account, model: Model, view: Int) {
//        model.addAttribute("loanForm", LoanForm())
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/save"))
    fun save(loanForm: LoanForm,@AuthenticationPrincipal user: User): String {
        loanService.save(loanForm,user.lastUsedAccount!!)
        return "redirect:/property/loans/input"
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    fun deleteLoan(@PathVariable id: String): MutableMap<String, String>? {
        loanService.delete(id.toLong())
        return Collections.singletonMap("result", "ok")

    }
}