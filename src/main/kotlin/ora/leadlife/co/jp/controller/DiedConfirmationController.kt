package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.OraConfiguration
import ora.leadlife.co.jp.form.DiedConfirmationForm
import ora.leadlife.co.jp.model.Bereave
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.BereaveService
import ora.leadlife.co.jp.service.FileUploadService
import ora.leadlife.co.jp.service.SettingService
import ora.leadlife.co.jp.util.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.NumberUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@Controller
@PropertySource("classpath:/path.properties")
@RequestMapping("/diedConfirmation")
class DiedConfirmationController {

    @Autowired
    lateinit var bereaveService: BereaveService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var settingService: SettingService

    @Autowired
    lateinit var oraConfiguration: OraConfiguration

    @Autowired
    lateinit var fileUploadService: FileUploadService

    @Autowired
    lateinit var sender: MailSender

    @Value("\${upload.dieConfirmation}")
    private var path: String = ""

    val logger: Logger = LoggerFactory.getLogger(DiedConfirmationController::class.java)

    @GetMapping(path = ["/list"])
    fun list(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("list", bereaveService.findByUser(user))
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "diedConfirmation/list"
    }

    @GetMapping(path = ["/{id}"])
    fun index(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        // 他のユーザーが死亡確認日を入力していないか
        if(Pattern.matches(".*[a-zA-Z]+.*", id) && bereaveService.findById(id.toLong()) == null ) {
            return "redirect:/"
        }
        val other = getOther(user, id.toLong())

        if(other != null) {
            if (other.diedDate != null) {
                return "redirect:/diedConfirmation/confirm/${other.account.id}"
            }
            model.addAttribute("bereave", bereaveService.findById(id.toLong()))
            model.addAttribute(DiedConfirmationForm())
            model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
            return "diedConfirmation/index"
        } else {
            return "redirect:/"
        }

    }

    @GetMapping("/diedNotification")
    fun diedNotification(): String {
        return "diedConfirmation/notification_document"
    }

    private fun getOther(user: User, id: Long): Bereave? {
        val bereaveList : List<Bereave> = bereaveService.findByAccount( bereaveService.findById(id)!!.account)

        bereaveList.forEach {
            if (it.user.id != user.id)
                return it
        }
        return null
    }

    @PostMapping("/register")
    fun register(model: Model, @ModelAttribute @Valid diedConfirmationForm: DiedConfirmationForm, bindingResult: BindingResult, request: HttpServletRequest): String {
        return registerData(
                bindingResult,
                model,
                diedConfirmationForm,
                request = request
        )
    }

    private fun registerData(bindingResult: BindingResult, model: Model, diedConfirmationForm: DiedConfirmationForm, isConfirm: Boolean = false, request: HttpServletRequest): String {
        val result: String
        when {
            bindingResult.hasErrors() -> {
                model.addAttribute("bereave", bereaveService.findById(diedConfirmationForm.id.toLong()))
                result = if (isConfirm)
                    "diedConfirmation/confirm"
                else
                    "diedConfirmation/index"
            }
            else -> {
                val bereave = bereaveService.findById(diedConfirmationForm.id.toLong())
                bereave!!.diedDate = diedConfirmationForm.date
                bereaveService.save(bereave)
                //start:for uploading image
                val account = accountService.findById(bereave.account.id.toString())
                if (diedConfirmationForm.file1 != null ) {
                    val file1 = fileUploadService.save(path+"file1/", diedConfirmationForm.file1!!, account.id.toString())
                    account.size1 = file1.fileSize
                    account.path1 = file1.filePath.toString()
                }
                if(diedConfirmationForm.file2 != null){
                    val file2 = fileUploadService.save(path+"file2/", diedConfirmationForm.file2!!, account.id.toString())
                    account.size2 = file2.fileSize
                    account.path2 = file2.filePath.toString()
                }
                accountService.save(account)
                //end
                result = "diedConfirmation/done"
                if (!isConfirm) {
                    val bereaveList = bereaveService.findByAccount(bereave.account)
                    bereaveList.forEach {
                        val mailHost = Util.getMailHost(request, "/diedConfirmation/confirm/", oraConfiguration)
                        sendMail(bereave, mailHost)
                    }
                }
            }
        }

        return result
    }

    @GetMapping(path = ["/confirm/{id}"])
    fun confirm(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: String): String {
        val bereave = bereaveService.findByAccountAndUser(
                account = accountService.findById(id),
                user = user
        ).first()
        val other = getOther(user, bereave.id!!)
        return if (other != null) {
            model.addAttribute("bereave", bereave)
            model.addAttribute("other", other)
            val diedConfirmationForm = DiedConfirmationForm(id = bereave.id.toString(), date = other.diedDate)
            model.addAttribute(diedConfirmationForm)
            "diedConfirmation/confirm"
        } else {
            "redirect:/diedConfirmation/index/${bereave.id}"
        }
    }

    @PostMapping("/registerConfirm")
    fun registerConfirm(model: Model, @Valid diedConfirmationForm: DiedConfirmationForm, bindingResult: BindingResult, request: HttpServletRequest): String {
        return registerData(
                bindingResult = bindingResult,
                model = model,
                diedConfirmationForm = diedConfirmationForm,
                isConfirm = true,
                request = request
        )
    }

    private fun makeMailBody(bereave: Bereave, url: String): String {
        return """
            この度は生前整理アプリケーションをご利用頂き有り難う御座います。
            ${bereave.user.name}さんより${bereave.account.user.name}さんの死亡日の登録がありました。
            以下のリンクをクリックし、内容をご確認ください。
            $url${bereave.account.id}
                """
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
}