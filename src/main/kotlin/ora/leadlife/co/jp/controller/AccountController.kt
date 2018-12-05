package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.AccountForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * アカウント関連
 */
@Controller
@RequestMapping("/account")
class AccountController {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var userService: UserService
    @GetMapping
    fun index(model: Model): String {
        return "account/index"
    }

    @GetMapping(path = arrayOf("/list"))
    fun list(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("accountList", accountService.findByUser(user))
        model.addAttribute(AccountForm())
        return "account/list"
    }

    @PostMapping(path = arrayOf("/add"))
    fun add(model: Model, @Valid accountForm: AccountForm, bindingResult: BindingResult, @AuthenticationPrincipal user: User): String {
        val result: String
        when {
            bindingResult.hasErrors() -> {
                model.addAttribute("accountList", accountService.findByUser(user))
                result = "account/list"
            }
            else -> {
                accountService.add(accountForm = accountForm, user = user)
                result = "redirect:/account/list"
            }
        }
        return result
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    fun delete(model: Model, @PathVariable id: String): Boolean {
        val account = accountService.findById(id)
        val check = userService.existsByLastUsedAccount(account)
        if(!check){
            accountService.deleteById(id)
        }
        return check
    }


    @RequestMapping(value = ["/change/{id}"], method = arrayOf(RequestMethod.GET))
    fun change(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        val account = accountService.findById(id)
        user.lastUsedAccount = account
        userService.save(user)
        return "account/done"
    }
}