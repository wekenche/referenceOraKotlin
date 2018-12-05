package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.FileType
import ora.leadlife.co.jp.form.MessageForm
import ora.leadlife.co.jp.model.Message
import ora.leadlife.co.jp.model.MessageAttachment
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.servlet.http.HttpSession


@Controller
@PropertySource("classpath:path.properties")
@RequestMapping("/message")
class MessageController {
    @Autowired
    lateinit var messageService: MessageService

    @Autowired
    lateinit var messageCategoryService: MessageCategoryService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var messageAttachmentService: MessageAttachmentService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var fileUploadService: FileUploadService

    @Value("\${upload.message}")
    lateinit var upload: String

    @GetMapping
    fun index(model: Model): String {
        return "message/index"
    }

    @GetMapping(path = arrayOf("/list"))
    fun list(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("messageList", messageService.findByAccountOrderByUpdatedAtDesc(user.lastUsedAccount!!))
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "message/list"
    }

    @GetMapping("/list/{id}")
    fun listByAccount(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long): String {
        val account = accountService.findById(id.toString())
        model.addAttribute("messageList", messageService.findByAccountOrderByUpdatedAtDesc(account))
        model.addAttribute("viewFlag", true)
        return "message/list"
    }

    @GetMapping(path = arrayOf("/detail/{id}"))
    fun detail(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long, @RequestParam viewFlag: String): String {
        val message = messageService.findById(id)
        val messageAttachment = messageAttachmentService.findByMessage(message)

        model.addAttribute("category", message.messageCategory.name)
        model.addAttribute("title", message.title)
        model.addAttribute("id", user.lastUsedAccount!!.id)
        model.addAttribute("message", message)
        model.addAttribute("messageAttachment", messageAttachment)
        model.addAttribute("viewFlag", viewFlag == "true")
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)

        return "message/detail"
    }

    private fun getMessage(messageForm: MessageForm): Message? {
        var message: Message? = null
        if (messageForm.messageId != 0L) {
            message = messageService.findById(messageForm.messageId)
        }
        return message
    }

    @GetMapping(path = arrayOf("/input1"))
    fun input1(model: Model, messageForm: MessageForm, @AuthenticationPrincipal user: User): String {
        val message: Message? = getMessage(messageForm)
        if (message != null) {
            messageForm.messageCategory = message.messageCategory.id!!
            messageForm.messageType = message.messageType
        }

        val messageCategories = messageCategoryService.findAll()
        model.addAttribute("categories", messageCategories)
        model.addAttribute("fileTypes", FileType.values().asList())
        model.addAttribute(messageForm)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "message/input1"
    }

    @PostMapping(path = arrayOf("/input1"))
    fun input1_b(model: Model, messageForm: MessageForm): String {
        val messageCategories = messageCategoryService.findAll()
        model.addAttribute("categories", messageCategories)

        return "message/input1"
    }

    @RequestMapping(value = "/input2", method = arrayOf(RequestMethod.GET,RequestMethod.POST))
    fun input2(model: Model, messageForm: MessageForm, bindingResult: BindingResult,
               @AuthenticationPrincipal user: User): String {
        val message: Message? = getMessage(messageForm)
        if (message != null) {
            messageForm.messageId = message.id!!
            messageForm.messageTitle = message.title
            messageForm.messageComment = message.comment
            messageForm.messageAttachment = messageForm.messageAttachment
            model.addAttribute("messageAttachmentList", message.messageAttachmentList)
        }

        val category = messageCategoryService.findOne(messageForm.messageCategory)

//        model.addAttribute("category", category.name)
//        model.addAttribute("title", messageForm.messageTitle)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)

        return "message/input2"
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model,
                messageForm: MessageForm,
                bindingResult: BindingResult,
                @RequestParam(value = "action", required = false) action: String,
                @RequestParam(value = "file", required = false) file: MultipartFile?,
                @AuthenticationPrincipal user: User,
                session: HttpSession
    ): String {
        if (action == "back") {
            return "redirect:/message/input1"
        }

        val allow= fileUploadService.allowUpload(user.lastUsedAccount!!)

        if(file != null) {

                val path = upload
                val filename = Instant.now().truncatedTo(ChronoUnit.SECONDS).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("uuuuMMdd'T'HHmmss")) + "." + File(file!!.originalFilename).extension

                if (!allow) {
                    bindingResult.rejectValue("messageAttachment", "upload.limit")
                } else {
                    if (file.bytes.isNotEmpty()) {
                        if (!File(path).exists())
                            File(path).mkdirs()

                        try {
                            val bytes = file.bytes
                            val buffStream = BufferedOutputStream(
                                    FileOutputStream(File(path + filename)))
                            buffStream.write(bytes)
                            buffStream.close()

                            println("You have successfully uploaded " + filename)
                            messageForm.messageAttachment = path + filename
                        } catch (e: Exception) {
                            bindingResult.rejectValue("messageAttachment", "You failed to upload " + filename + ": " + e.message)
                        }
                    } else {
                        println("File is empty.")
                    }


                }

        }

        val category = messageCategoryService.findOne(messageForm.messageCategory)
        model.addAttribute("category", category.name)
        model.addAttribute("id", messageForm.messageId!!)
        model.addAttribute("title", messageForm.messageTitle)
        model.addAttribute("type", messageForm.messageType)
        model.addAttribute("comment", messageForm.messageComment)
        model.addAttribute("attachment", messageForm.messageAttachment)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)

        val result = when {
            bindingResult.hasErrors() -> "message/input2"
            else -> {
                "message/confirm"
            }
        }

        return result
    }

    @PostMapping(path = arrayOf("/done"))
    fun done(model: Model,
             messageForm: MessageForm,
             @AuthenticationPrincipal user: User,
             redirectAttributes: RedirectAttributes,
             @RequestParam(value = "action", required = true) action: String
    ): String {
        if (action == "edit") {
            redirectAttributes.addFlashAttribute("messageForm",messageForm)
            return "redirect:/message/input2"
        }

        var message = Message()
        if (messageForm.messageId != 0L)
            message = messageService.findById(messageForm.messageId)

        message.title = messageForm.messageTitle
        message.comment = messageForm.messageComment
        message.messageType = messageForm.messageType
        message.messageCategory = messageCategoryService.findOne(messageForm.messageCategory)
        message.account = user.lastUsedAccount!!

        val msg = messageService.save(message)

        if (!messageForm.messageAttachment.isNullOrEmpty()) {
            val messageAttachment = MessageAttachment()
            val attachment = File(messageForm.messageAttachment)
            val to = upload + user.lastUsedAccount!!.id + "/" + msg.id + "." + attachment.extension
            val copy = attachment.copyTo(File(to),true)
            attachment.delete()
            messageAttachment.name = copy.nameWithoutExtension
            messageAttachment.filePath = copy.path
            messageAttachment.fileType = message.messageType
            messageAttachment.fileSize = copy.length()
            messageAttachment.message.id = msg.id
            messageAttachmentService.save(messageAttachment)
        }


        return "redirect:/message/list"
    }

    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): String {
        val message = messageService.findById(id)
        message.messageAttachmentList?.forEach {
            messageAttachmentService.delete(it.id!!)
        }
        messageService.delete(message)
        return "redirect:/message/list"
    }
}


