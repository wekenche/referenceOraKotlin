package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.config.FileType
import ora.leadlife.co.jp.form.IndividualNoteCategoryForm
import ora.leadlife.co.jp.form.IndividualNoteForm
import ora.leadlife.co.jp.model.*
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
import java.io.File

/**
 * 私のこと
 */
@Controller
@PropertySource("classpath:path.properties")
@RequestMapping("/individualNote")
class IndividualNoteController {

    @Autowired
    lateinit var individualNoteService: IndividualNoteService

    @Autowired
    lateinit var individualNoteAttachmentService: IndividualNoteAttachmentService

    @Autowired
    lateinit var individualNoteCategoryService: IndividualNoteCategoryService

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var bereaveService: BereaveService

    @Autowired
    lateinit var fileUploadService: FileUploadService


    @Value("\${upload.individualNote}")
    lateinit var upload: String

    val medicineCode = "001"

    private fun loadIndex(account: Account, model: Model) {
        val individualNoteCategoryList = individualNoteCategoryService.findAllByAccountId(account)
        model.addAttribute("individualNoteCategoryList", individualNoteCategoryList)
        model.addAttribute(IndividualNoteCategoryForm())
    }

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User,@RequestParam(required = false) aboutMeViewAccount: String?): String {
        if (aboutMeViewAccount.isNullOrBlank()) {
            model.addAttribute("viewFlag", true)
        }else {
            model.addAttribute("viewFlag", false)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        loadIndex(account = user.lastUsedAccount!!, model = model)
        return "individualNote/index"
    }

    @GetMapping("/view/{id}")
    fun view(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long,@RequestParam(required = false) aboutMeViewAccount: String?): String {
        loadIndex(accountService.findById(id.toString()), model)
        if (aboutMeViewAccount.isNullOrBlank()) {
            model.addAttribute("viewFlag", true)
        }else {
            model.addAttribute("viewFlag", false)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "individualNote/index"
    }

    @GetMapping(path = arrayOf("/list/{individualNoteCategoryId}"))
    fun list(model: Model, @PathVariable individualNoteCategoryId: Long, @RequestParam(required = false) aboutMeViewAccount: String?,@AuthenticationPrincipal user: User): String {
        if (isMedicineCategory(individualNoteCategoryId)){
            return "redirect:/medicine/input"
        }
        var account = user.lastUsedAccount!!
        if (aboutMeViewAccount.isNullOrBlank()) {
            model.addAttribute("viewFlag", true)
        }else{
            val temp:Account? = accountService.findById(aboutMeViewAccount!!)
            if(temp!=null){
                account = temp
            }
            model.addAttribute("viewFlag", false)
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        model.addAttribute("individualNoteCategoryId",individualNoteCategoryId)
        model.addAttribute("list", individualNoteService.findByIndividualNoteCategoryIdAndAccount(individualNoteCategoryId, account))
        return "individualNote/list"
    }

    @GetMapping(path = arrayOf("/individualNoteDetail/{id}"))
    fun detail(model: Model,
               @AuthenticationPrincipal user: User,
               @PathVariable id: Long,
               @RequestParam(required = false) aboutMeViewAccount: String?): String {
        val individualNote = individualNoteService.findById(id)
        if (!aboutMeViewAccount.isNullOrBlank()) {
            model.addAttribute("viewFlag", true)
            if (!bereaveService.isBereave(aboutMeViewAccount!!.toLong(), user.lastUsedAccount!!.id!!)) {
                return "redirect:/"
            }
        } else {
            if (individualNote != null) {
                if (individualNote.account.id != user.lastUsedAccount!!.id) {
                    return "redirect:/"
                }
            }else{
                return "redirect:/"
            }
        }
        val individualNoteAttachments = individualNoteAttachmentService.findByIndividualNote(individualNote!!)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        model.addAttribute("individualNoteAttachments",individualNoteAttachments )
        model.addAttribute("individualNote", individualNote)
        return "individualNote/detail"
    }

    private fun isMedicineCategory(individualNoteCategoryId: Long): Boolean {
        val individualNoteCategoryTemplate = individualNoteCategoryService.findById(individualNoteCategoryId)!!.individualNoteCategoryTemplate
        if (individualNoteCategoryTemplate != null && individualNoteCategoryTemplate.code == medicineCode)
            return true
        return false
    }

    @GetMapping(path = arrayOf("/input1"))
    fun input1(model: Model, @AuthenticationPrincipal user: User, @ModelAttribute("individualNoteForm") individualNoteForm: IndividualNoteForm,
               @RequestParam(required = false) individualNoteCategory : String? , redirectAttributes: RedirectAttributes) : String {
        val account = user.lastUsedAccount!!
        if(!individualNoteCategory.isNullOrEmpty()) {
            individualNoteForm.individualNoteCategory = individualNoteCategory!!.toLong()
        }
        if(!account.isPremium) {
            redirectAttributes.addFlashAttribute("individualNoteForm", individualNoteForm)
            return "redirect:/individualNote/input2"
        }
        val list = individualNoteCategoryService.findAllByAccountId(account)
        if (individualNoteForm.individualNoteCategory != 0L && isMedicineCategory(individualNoteForm.individualNoteCategory)) {
            return "redirect:/medicine/input"
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        model.addAttribute("categories", list)
        model.addAttribute("fileTypes", FileType.values().asList())
        model.addAttribute("form", individualNoteForm)

        return "individualNote/input1"
    }

    @RequestMapping("/input2", method = arrayOf(RequestMethod.GET,RequestMethod.POST))
    fun input2(model: Model, individualNoteForm: IndividualNoteForm, @AuthenticationPrincipal user: User): String {
        var filePreview = individualNoteForm.individualNoteAttachment
        if(individualNoteForm.individualId!=0L){
            val individualNote = individualNoteService.findById(individualNoteForm.individualId)
            filePreview = individualNoteAttachmentService.findOneByIndividualNote(individualNote!!)?.filePath
        }
        model.addAttribute("filePreview", filePreview)
        model.addAttribute("individualNoteForm", individualNoteForm)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "individualNote/input2"
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, individualNoteForm: IndividualNoteForm, bindingResult: BindingResult, @RequestParam(required = false, value="file") file: MultipartFile?,
                redirectAttributes: RedirectAttributes, @RequestParam(value = "action", required = true) action: String, @AuthenticationPrincipal user: User): String {
        val account = user.lastUsedAccount!!
        if (action == "back") {
            return if(account.isPremium){
                redirectAttributes.addFlashAttribute("individualNoteForm", individualNoteForm)
                "redirect:/individualNote/input1?individualNoteCategory=${individualNoteForm.individualNoteCategory}"
            } else {
                "redirect:/individualNote/list/${individualNoteForm.individualNoteCategory}"
            }
        }
        val allow = fileUploadService.allowUpload(account)
        if(allow) {
            if(file != null) {
                if(file.bytes.isNotEmpty()) {
                    val indFile = fileUploadService.save(upload, file, account.id.toString())
                    individualNoteForm.individualNoteAttachment = indFile.filePath!!
                }
            }
        }
        else {
            bindingResult.rejectValue("individualNoteAttachment", "upload.limit")
        }


        val category = individualNoteCategoryService.findById(individualNoteForm.individualNoteCategory)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        model.addAttribute("individualNoteForm", individualNoteForm)
        model.addAttribute("category", category!!.name)
        model.addAttribute("title", individualNoteForm.individualTitle)


        return when {
            bindingResult.hasErrors() && !allow -> "individualNote/input2"
            else -> {
                "individualNote/confirm"
            }
        }
    }

    @PostMapping(path = arrayOf("/done"))
    fun done(model: Model, individualNoteForm: IndividualNoteForm, @AuthenticationPrincipal user: User,redirectAttributes: RedirectAttributes,@RequestParam(value = "action", required = true) action: String): String {
        if (action == "edit") {
            redirectAttributes.addFlashAttribute("individualNoteForm", individualNoteForm)
            return "redirect:/individualNote/input2"
        }
        val indNote = individualNoteService.save(IndividualNote(id = individualNoteForm.individualId, title = individualNoteForm.individualTitle, type = individualNoteForm.messageType,
                contents = individualNoteForm.individualNoteContents, account = user.lastUsedAccount!!, accountId = user.lastUsedAccount!!.id, individualNoteCategory = individualNoteCategoryService.findById(individualNoteForm.individualNoteCategory)!!, individualNoteCategoryId = individualNoteForm.individualNoteCategory))
        if(!individualNoteForm.individualNoteAttachment.isBlank()) {
            val indAttachment = File(individualNoteForm.individualNoteAttachment)
            val to = upload + user.lastUsedAccount!!.id + "/" + indNote.id + "." + indAttachment.extension
            val copy = indAttachment.copyTo(File(to),true)
            indAttachment.delete()
            var attachmentId = 0L
            if (individualNoteAttachmentService.findOneByIndividualNote(indNote)?.id != null) {
                attachmentId = individualNoteAttachmentService.findOneByIndividualNote(indNote).id!!
            }
            individualNoteAttachmentService.save(IndividualNoteAttachment(id = attachmentId,individualNoteId = indNote.id, name = copy.nameWithoutExtension,
                    fileType = individualNoteForm.messageType, filePath = copy.path, fileSize = copy.length(), individualNote = indNote))
        }
        return "redirect:/individualNote/list/"+individualNoteForm.individualNoteCategory
    }

    @PostMapping(path = arrayOf("/addCategory"))
    fun addCategory(model: Model, @AuthenticationPrincipal user: User, individualNoteCategoryForm: IndividualNoteCategoryForm): String {
        val individualNoteCategory = IndividualNoteCategory(
                name = individualNoteCategoryForm.name,
                accountId = user.lastUsedAccount!!.id,
                account = user.lastUsedAccount!!
        )
        individualNoteCategoryService.save(individualNoteCategory)
        return "redirect:/individualNote"
    }

    @GetMapping(path = arrayOf("/update/{id}"))
    fun update(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long): String {
        val account = user.lastUsedAccount!!
        val list = individualNoteCategoryService.findAllByAccountId(account)
        model.addAttribute("categories", list)
        val i = individualNoteService.findById(id)
        val individualNoteForm = IndividualNoteForm(individualId = id, individualNoteCategory = i!!.individualNoteCategoryId, messageType = i.type!!, individualTitle = i.title!!, individualNoteContents = i.contents!!)
        model.addAttribute("fileTypes", FileType.values().asList())
        model.addAttribute("form", individualNoteForm)
        return "individualNote/input1"
    }

    @RequestMapping(path = arrayOf("/delete/{id}"), method = arrayOf(RequestMethod.GET))
    fun delete(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long): String {
        val individualNote = individualNoteService.findById(id)
        individualNote!!.individualNoteAttachmentList?.forEach {
            individualNoteAttachmentService.delete(it.id!!)
        }
        individualNoteService.delete(id)
        return "redirect:/individualNote/list/"+ individualNote!!.individualNoteCategoryId
    }

    @RequestMapping(path = arrayOf("/delete/category/{id}"), method = arrayOf(RequestMethod.GET))
    fun deleteCategory(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: Long): String {
        individualNoteCategoryService.delete(id)
        return "redirect:/individualNote/"
    }
}