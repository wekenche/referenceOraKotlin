package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.PasswordForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


/**
 * 無料会員への移行
 */
@Controller
@RequestMapping("/freeMigration")
class FreeMigrationController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var paidRegistrationService: PaidRegistrationService

    @Autowired
    lateinit var bereaveService: BereaveService

    @Autowired
    lateinit var settingService: SettingService

    @Autowired
    lateinit var sender: MailSender

    val logger: Logger = LoggerFactory.getLogger(FreeMigrationController::class.java)

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("nextUpdateDate", accountService.nextPaymentUpdateDate(user.lastUsedAccount!!.id!!))
        return "freeMigration/index"
    }

    @GetMapping(path = arrayOf("/input"))
    fun input(model: Model, @AuthenticationPrincipal user: User): String {

        if (!user.lastUsedAccount!!.isPremium) {
            return "redirect:/freeMigration/"
        }

        setAttributeForInput(user, model)

        return "freeMigration/input"
    }

    private fun setAttributeForInput(user: User, model: Model) {

        model.addAttribute(PasswordForm())
        model.addAttribute("user", user)
    }

    @PostMapping("/confirm")
    fun confirm(model: Model, passwordForm: PasswordForm, @AuthenticationPrincipal user: User): String {
        if (!userService.match(passwordForm.password, user)) {
            return "redirect:/freeMigration/input"
        }

        model.addAttribute("user", user)

        return "redirect:/freeMigration/done"
    }

    @GetMapping(path = arrayOf("/done"))
    fun done(model: Model, @AuthenticationPrincipal user: User): String {
        val account = accountService.makeFree(user.lastUsedAccount!!)

        if(!account.isPremium) {
            sendMail(user.email, null)

            val bereaves = bereaveService.findByAccount(account)
            bereaves.forEach {
                val user = userService.findById(it.userId)
                sendMail(user.email,user.name)
            }
        }

        return "freeMigration/done"
    }

    private fun sendMail(email : String, name: String?) {
        val msg = SimpleMailMessage()

        msg.from = settingService.getSystem().email
        msg.setTo(email)

        if(name.isNullOrBlank()) {
            msg.subject = "この度は生前整理アプリケーションをご利用頂き有り難う御座います。"
            msg.text = "有料会員から無料会員への変更が完了致しました。\n" +
                    "有料会員のみ使用可能なデータは削除されましたことをご了承願います。"
        }
        else {
            msg.subject = "この度は生前整理アプリケーションをご利用頂き有り難う御座います。"
            msg.text = name + " さんが、有料会員から無料会員へ変更致しました。\n" +
                    "有料会員向けのデータが、"+ name +" さんがお亡くなりになった際に閲覧出来なくなりましたことをご了承願います。"
        }

//        logger.info("FROM: "+ msg.from +" TO: "+ msg.to +" SUBJECT: "+ msg.subject +" TEXT: "+ msg.text)
        this.sender.send(msg)
    }
}