package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.AuthenticationMethod
import ora.leadlife.co.jp.form.UserForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

/**
 * ユーザー関連のコントローラ
 */
@Controller
@RequestMapping("/")
class UserController {
    @Autowired
    lateinit var userService: UserService
    /**
     * ログイン入力画面
     */
    @RequestMapping("/login/input")
    fun loginInput(modelAndView: ModelAndView): ModelAndView {
        modelAndView.viewName = "login/input"
        return modelAndView
    }


    /**
     * ログアウト
     */
    @RequestMapping("/logout")
    fun logout(modelAndView: ModelAndView): ModelAndView {
        modelAndView.viewName = "login/logout"
        return modelAndView
    }

    @GetMapping("/user/index")
    fun input(model: Model, userForm: UserForm, @AuthenticationPrincipal user: User): String {
        if(user.authenticationMethod == AuthenticationMethod.FINGERPRINT.name){
            userForm.authenticationMethodFingerprint = true
        }
        else {
            userForm.authenticationMethodPassword = true
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "user/index"
    }

    @PostMapping("/user/done")
    fun done(model: Model, @Valid userForm: UserForm, bindingResult: BindingResult, @AuthenticationPrincipal user: User): String {
        userForm.email = userForm.email.trim(' ')
        val check = userService.findByEmail(userForm.email)
        if (check != null && check.id != user.id) {
            bindingResult.rejectValue("email", "duplicate")
            return "user/index"
        }
        if(!userForm.oldPassword.isNullOrBlank()) {
            if (userService.match(userForm.oldPassword!!,user)) {
                if (userForm.newPassword != userForm.newPasswordConfirm) {
                    bindingResult.rejectValue("newPassword","not.equal.password")
                    bindingResult.rejectValue("newPasswordConfirm","not.equal.password")
                }

                if(userForm.newPassword!!.length < 8 || userForm.newPassword!!.length > 12){
                    bindingResult.reject("newPassword","8から12の文字数で入力してください")
                }

                if(userForm.newPasswordConfirm!!.length < 8 || userForm.newPasswordConfirm!!.length > 12){
                    bindingResult.reject("newPasswordConfirm","8から12の文字数で入力してください")
                }
            }
            else {
                bindingResult.rejectValue("oldPassword", "invalid")
            }
        }

        val result = when {
            bindingResult.hasErrors() -> "user/index"
            else -> {
                "user/done"
            }
        }

        if(result == "user/done") {
            if(!userForm.username!!.isNullOrBlank()){
                user.name = userForm.username
            }
            if(!userForm.email!!.isNullOrBlank()) {
                user.email = userForm.email!!
            }
            if(!userForm.newPassword!!.isNullOrBlank()){
                user.encryptedPassword = BCryptPasswordEncoder().encode(userForm.newPassword)
            }
            if(user.authenticationMethod != userForm.getAuthenticationMethod()) {
                user.authenticationMethod = userForm.getAuthenticationMethod()
            }
            userService.save(user)
        }

        return result
    }
}