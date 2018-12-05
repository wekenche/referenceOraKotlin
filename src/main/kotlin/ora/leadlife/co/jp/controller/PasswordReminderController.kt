package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.OraConfiguration
import ora.leadlife.co.jp.form.PasswordReminderForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.SettingService
import ora.leadlife.co.jp.service.UserService
import ora.leadlife.co.jp.util.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * パスワードリマインダー
 */
@Controller
@RequestMapping("/passwordReminder")
class PasswordReminderController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var sender: MailSender

    @Autowired
    lateinit var settingService: SettingService

    @Autowired
    lateinit var oraConfiguration: OraConfiguration

    val logger: Logger = LoggerFactory.getLogger(PasswordReminderController::class.java)

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute(PasswordReminderForm())
        return "passwordReminder/index"
    }

    @PostMapping
    fun send(@Valid passwordReminderForm: PasswordReminderForm, bindingResult: BindingResult, request: HttpServletRequest): String {
        passwordReminderForm.email = passwordReminderForm.email.trim(' ')
        if (bindingResult.hasErrors()) {
            return "passwordReminder/index"
        }

        var user = userService.findByEmail(passwordReminderForm.email)
        if (user == null) {
            bindingResult.rejectValue("email", "not.exist.email")
            return "passwordReminder/index"
        }
        val result: String
        user.isValid = true
        user.resetPasswordToken = Util.encrypt("${System.currentTimeMillis()}~${user.email}")
        userService.save(user)
        val mailHost = Util.getMailHost(request, "/password/input", oraConfiguration)
        sendMail(user, mailHost)
        result = "passwordReminder/complete"
        return result
    }

    private fun sendMail(user: User, url: String) {
        val msg = SimpleMailMessage()
        msg.from = settingService.getSystem().email
        msg.setTo(user.email)
        msg.subject = "生前整理 パスワードリセット"
        msg.text = makeMailBody(user, url)
        logger.info(msg.text)
        this.sender.send(msg)
    }

    private fun makeMailBody(user: User, url: String): String {
        return """
            この度は生前整理アプリケーションをご利用頂き有り難う御座います。
            以下のリンクをクリックし、パスワードをリセットしてください。
            ${url}?q=${URLEncoder.encode(user.resetPasswordToken, "UTF-8")}
            """
    }
}