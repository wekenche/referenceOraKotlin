package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.TestamentForm
import ora.leadlife.co.jp.form.TestamentSpecialistForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.SpecialistForTestamentService
import ora.leadlife.co.jp.service.TestamentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/testament")
class TestamentController {

    @Autowired lateinit var testamentService: TestamentService
    @Autowired lateinit var specialistForTestamentService: SpecialistForTestamentService
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
        return "testament/testament_about"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        if(testamentService.findByAccount(account) != null) {
            if(testamentService.findByAccount(account)!!.account.id!! == account.id) {
                val t = testamentService.findByAccount(account)!!
                val form = TestamentForm(testId = t.id!! , existsTestament = t.existsTestament , testamentType = t.testamentType!! , storingPlace = t.storingPlace!!)
                if(specialistForTestamentService.findByAccount(account).isNotEmpty()) {
                    val list : ArrayList<TestamentSpecialistForm> = ArrayList()
                    specialistForTestamentService.findByAccount(account).forEach {
                        val testament = TestamentSpecialistForm(name = it.name!!, name2 = it.name2!!, postCode1 = it.postCode1!!, postCode2 = it.postCode2!! , streetAddress = it.address!! , number = it.tel!!)
                        list.add(testament)
                    }
                    form.tsWrapper = list
                }
                model.addAttribute("form",form)
            } else {
                model.addAttribute("form" , TestamentForm())
            }
        } else {
            model.addAttribute("form" , TestamentForm())
        }
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, testamentForm: TestamentForm, @AuthenticationPrincipal user: User): String {
        testamentService.save(testamentForm,user)
        return "redirect:/testament/add"
    }
}