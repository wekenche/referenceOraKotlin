package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.WithdrawalForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.PaidRegistrationService
import ora.leadlife.co.jp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

/**
 * 退会用
 */
@Controller
@RequestMapping("/withdrawal")
class WithdrawalController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var paidRegistrationService: PaidRegistrationService

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute(WithdrawalForm())
        return "withdrawal/index"
    }

    @PostMapping
    fun confirm(model: Model, @Valid withdrawalForm: WithdrawalForm, bindingResult: BindingResult, @AuthenticationPrincipal user: User): String {

        if (!userService.match(withdrawalForm.password, user)) {
            bindingResult.rejectValue("password", "invalid")
            return "withdrawal/index"
        }

        return "withdrawal/confirm"
    }

    @GetMapping(path = arrayOf("/apple"))
    fun indexApple(model: Model): String {
        return "withdrawal/accountDeactivationProcedure"
    }

    @GetMapping(path = arrayOf("/done"))
    fun done(model: Model, @AuthenticationPrincipal user: User): String {
        accountService.findByUser(user).forEach {
            if (it.isPremium)
                paidRegistrationService.withdraw(it)
        }
        userService.deleteByEmail(user.email)
        SecurityContextHolder.clearContext();
        return "withdrawal/done"
    }
}