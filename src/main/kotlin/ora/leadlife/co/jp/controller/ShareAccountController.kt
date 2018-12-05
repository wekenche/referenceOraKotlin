package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.OraConfiguration
import ora.leadlife.co.jp.form.ShareAccountForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Bereave
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.BereaveService
import ora.leadlife.co.jp.service.SettingService
import ora.leadlife.co.jp.util.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 共有アカウント設定
 */
@Controller
@RequestMapping("/shareAccount")
class ShareAccountController {
    @Autowired
    lateinit var bereaveService: BereaveService

    @Autowired
    lateinit var settingService: SettingService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var oraConfiguration: OraConfiguration

    @Autowired
    lateinit var sender: MailSender

    val logger: Logger = LoggerFactory.getLogger(ShareAccountController::class.java)

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User): String {
        val result = makeIndex(model, user.lastUsedAccount!!)
        model.addAttribute(ShareAccountForm())
        return result
    }

    @GetMapping(path = arrayOf("/view/{id}"))
    fun view(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long): String {
        if(accountService.findByIdToCheckNull(id.toString()) == null) {
            return "redirect:/"
        }
        makeIndex(model, accountService.findById(id.toString()))
        return "shareAccount/view"
    }

    @DeleteMapping(path = arrayOf("/delete/{id}"))
    @ResponseBody
    fun delete(@PathVariable id: String) = bereaveService.delete(id.toLong())

    @GetMapping(path = arrayOf("/input"))
    fun input(model: Model): String {
        return "shareAccount/input"
    }

    @PostMapping(path = arrayOf("/saveShareAccount"))
    fun register(model: Model, @Valid shareAccountForm: ShareAccountForm, bindingResult: BindingResult, @AuthenticationPrincipal user: User, request: HttpServletRequest): String {
        val result: String
        when {
            bindingResult.hasErrors() -> {
                result = makeIndex(model, user.lastUsedAccount!!)
            }
            else -> {
                val bereave = bereaveService.add(user.lastUsedAccount!!, shareAccountForm, bindingResult)
                if (bereave != null) {
                    val mailHost = Util.getMailHost(request, "/sharedAccount/approve/", oraConfiguration)
                    sendMail(bereave, mailHost)
                    result = "redirect:/shareAccount"
                } else {
                    result = makeIndex(model, user.lastUsedAccount!!)
                }
            }
        }
        return result
    }

    private fun sendMail(bereave: Bereave, url: String) {
        val msg = SimpleMailMessage()
        msg.from = settingService.getSystem().email
        msg.setTo(bereave.user.email)
        msg.subject = "生前整理 共有承認依頼"
        msg.text = makeMailBody(bereave, url)
        logger.info(msg.text)
        this.sender.send(msg)
    }

    private fun makeMailBody(bereave: Bereave, url: String): String {
        return """
            この度は生前整理アプリケーションをご利用頂き有り難う御座います。
            ${bereave.account.user.name}さんより共有依頼が御座いました。
            以下のリンクをクリックし、共有設定を完了させて下さい。
            $url${bereave.id}
                """
    }

    private fun makeIndex(model: Model, account: Account): String {
        model.addAttribute("list", bereaveService.findByAccount(account))
        return "shareAccount/index"
    }

    @GetMapping(path = arrayOf("description"))
    fun description(model: Model): String {
        return "shareAccount/description"
    }

}