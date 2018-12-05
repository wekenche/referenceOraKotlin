package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.PasswordForm
import ora.leadlife.co.jp.service.UserService
import ora.leadlife.co.jp.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * パスワードリマインダー
 */
@Controller
@RequestMapping("/password")
class PasswordController {
    @Autowired
    lateinit var userService: UserService

    @GetMapping
    fun index(model: Model): String {
        return "password/index"
    }

    @GetMapping(path = arrayOf("/input"))
    fun input(@RequestParam q: String, model: Model): String {
        val passwordForm = PasswordForm()
        passwordForm.resetPasswordToken = q
        model.addAttribute(passwordForm)
        return "password/input"
    }

    @PostMapping
    fun done(@Valid passwordForm: PasswordForm, bindingResult: BindingResult, request: HttpServletRequest): String {
        if (bindingResult.hasErrors()) {
            return "password/input"
        }
        val email = Util.decrypt(passwordForm.resetPasswordToken).split("~")[1]
        val user = userService.findByEmail(email)
        if (user == null || user.resetPasswordToken != passwordForm.resetPasswordToken) {
            bindingResult.rejectValue("resetPasswordToken", "invalid.access")
            return "password/input"
        }

        user.encryptedPassword = userService.passwordEncoder().encode(passwordForm.password)
        user.resetPasswordToken = null
        userService.save(user)

        return "password/done"
    }
}