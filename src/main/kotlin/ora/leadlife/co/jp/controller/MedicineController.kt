package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.HealthsForm
import ora.leadlife.co.jp.model.HealthAttachment
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Controller
@PropertySource("classpath:/path.properties")
@RequestMapping("/medicine")
class MedicineController {
    @Autowired
    lateinit var medicineService: MedicineService
    @Autowired
    lateinit var healthService: HealthService
    @Autowired
    lateinit var fileUploadService: FileUploadService
    @Autowired
    lateinit var healthAttachmentService: HealthAttachmentService
    @Autowired
    lateinit var accountService: AccountService

    @Value("\${upload.medicines}")
    private var path: String = ""

    @GetMapping
    fun index(): String {
        return "health/index"
    }

    @GetMapping("/input")
    fun input(model: Model, @AuthenticationPrincipal user: User, @RequestParam(required = false) aboutMeViewAccount: String?): String {
        var account = user.lastUsedAccount!!

        if (aboutMeViewAccount.isNullOrBlank()) {
            model.addAttribute("aboutMeViewFlag", 0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            model.addAttribute("aboutMeViewFlag", 1)
        }

        val healths = healthService.findByAccount(account)
        val attachment = healthAttachmentService.findByAccount(account)
        if (healths != null) {
            model.addAttribute("healthsForm", healths)
        } else {
            model.addAttribute("healthsForm", HealthsForm())
        }
        model.addAttribute("isPremium", account.isPremium)
        model.addAttribute("fileList", attachment)
        model.addAttribute("medicineList", medicineService.findByAccount(account))
        return "health/input"
    }

    @PostMapping("/save")
    fun save(healthsForm: HealthsForm, @AuthenticationPrincipal user: User): String {
        val account = user.lastUsedAccount!!
        val attachmentList = mutableListOf<HealthAttachment>()
        healthsForm.file?.forEach {
            if (!it.isEmpty) {
                val timeStamp = SimpleDateFormat("yyyyMMddHHmmss")
                val file = fileUploadService.save(path, it, timeStamp.format(Timestamp(System.currentTimeMillis()))+"-"+account.id)
                var type = "PICTURE"
                if (it.contentType.toLowerCase().contains("video")) {
                    type = "MOVIE"
                }
                attachmentList.add(HealthAttachment(filePath = file.filePath, fileType = type, fileName = file.fileName, fileSize = file.fileSize!!.toInt(), account = account))
            }
        }
        healthAttachmentService.save(account, attachmentList)
        medicineService.save(healthsForm, account)
        healthService.save(healthsForm, account)
        return "redirect:/medicine/input"
    }
}