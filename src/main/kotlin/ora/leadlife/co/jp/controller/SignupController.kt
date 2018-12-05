package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.OraConfiguration
import ora.leadlife.co.jp.form.SignupForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.SettingService
import ora.leadlife.co.jp.service.UserService
import ora.leadlife.co.jp.util.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.keygen.KeyGenerators
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


/**
 * サインアップ画面
 */
@Controller
@RequestMapping("/signup")
class SignupController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var sender: MailSender

    @Autowired
    lateinit var settingService: SettingService

    @Autowired
    lateinit var oraConfiguration: OraConfiguration

    val logger: Logger = LoggerFactory.getLogger(SignupController::class.java)

    /**
     * 入力画面
     */
    @GetMapping
    fun input(model: Model, signupForm: SignupForm): String {
        logger.info(KeyGenerators.string().generateKey())
        model.addAttribute(signupForm)
        return "signup/input"
    }


    @PostMapping
    fun registry(@Valid signupForm: SignupForm, bindingResult: BindingResult, request: HttpServletRequest,@RequestHeader(value = "User-Agent") header : String): String {
        if (userService.findByEmail(signupForm.email) != null) {
            bindingResult.rejectValue("email", "duplicate")
            return "signup/input"
        }
        val result: String
        when {
            bindingResult.hasErrors() -> result = "signup/input"
            else -> {
                val os = if (header.toLowerCase().indexOf("android") >= 1) {
                    "ANDROID"
                } else if (header.toLowerCase().indexOf("iphone") >= 1) {
                    "IOS"
                } else {
                    "OTHER"
                }
                val user = userService.registryUser(
                        name = signupForm.name,
                        email = signupForm.email.trim(' '),
                        password = signupForm.password,
                        shopCode = signupForm.shopCode,
                        authenticationMethod = signupForm.getAuthenticationMethod(),
                        os = os
                )
                val mailHost = Util.getMailHost(request, "/signup/confirmed", oraConfiguration)
                sendMail(user, mailHost, os)
                result = "signup/registry"
            }
        }
        return result
    }

    @GetMapping(path = arrayOf("/confirmed"))
    fun confirmed(@RequestParam q: String): String {
        val user = userService.findByEmail(Util.decrypt(q))
        if (user != null) {
            user.isValid = true
            userService.save(user)
            return "signup/confirmed"
        }
        return "redirect:/signup"
    }

    private fun sendMail(user: User, url: String, os : String) {
        val msg = SimpleMailMessage()
        msg.from = settingService.getSystem().email
        msg.setTo(user.email)
        msg.subject = "生前整理 会員登録確認メール"
        msg.text = makeMailBody(user, url, os)
        logger.info(msg.text)
        this.sender.send(msg)
    }

    private fun makeMailBody(user: User, url: String, os : String): String {
        return when (os == "ANDROID") {
            true -> """
            この度は生前整理アプリケーションをご利用頂き有り難う御座います。
            以下のリンクをクリックし、会員登録を完了させてください。
            ${url}?q=${URLEncoder.encode(Util.encrypt(user.email), "UTF-8")}
            なお、アプリを未インストールの場合は、以下よりインストールお願いします。
            https://play.google.com/store/apps/details?id=jp.co.leadlife.oraandroid
            """
            false -> """
            この度は生前整理アプリケーションをご利用頂き有り難う御座います。
            以下のリンクをクリックし、会員登録を完了させてください。
            ${url}?q=${URLEncoder.encode(Util.encrypt(user.email), "UTF-8")}
            """
        }
    }
}