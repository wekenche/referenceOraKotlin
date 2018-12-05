package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.FileUploadForm
import ora.leadlife.co.jp.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

@Service
class FileUploadService {
    @Autowired
    lateinit var messageAttachmentService: MessageAttachmentService

    @Autowired
    lateinit var individualNoteAttachmentService: IndividualNoteAttachmentService

    @Autowired
    lateinit var healthAttachmentService: HealthAttachmentService

    @Autowired
    lateinit var messageService: MessageService

    @Autowired
    lateinit var individualNoteService: IndividualNoteService

    fun save(path: String, file: MultipartFile, name: String): FileUploadForm {
        val fileUploadForm = FileUploadForm()
        if (file.bytes.isNotEmpty()) {
            val fileName = file.originalFilename
            val fileExtension = File(fileName).extension
            val fileRootDirectory = File(path)

            if (!fileRootDirectory.exists()) {
                fileRootDirectory.mkdirs()
            }
            val serverFile = File(fileRootDirectory.absolutePath + File.separator + name + "." + fileExtension)
            val stream = BufferedOutputStream(FileOutputStream(serverFile))
            stream.write(file.bytes)
            stream.close()

            println(serverFile.toString())
            fileUploadForm.filePath = path + name + "." + fileExtension //for relative path
            //fileUploadForm.filePath = serverFile.toString() //for absolute path
            fileUploadForm.fileName = fileName
            fileUploadForm.fileSize = file.bytes.size.toLong()
            fileUploadForm.fileType = file.contentType

        }
        return fileUploadForm
    }

    fun delete(path: String, file: MultipartFile, name: String) {
        val fileExtension = File(file.originalFilename).extension
        val f = File(path + name + "." + fileExtension)
        if (f.exists() && !f.isDirectory) {
            f.delete()
        }
    }

    fun allowUpload(account: Account): Boolean {
        var allow = true
        var total = 0

        messageAttachmentService.findByMessageIn(
                messageService.findByAccount(account)
        ).map { it.fileSize }.forEach {
            if (it != null)
                total += it.toInt()
        }

        individualNoteAttachmentService.findByIndividualNoteIn(
                individualNoteService.findByAccount(account)
        ).map { it.fileSize }.forEach {
            if (it != null)
                total += it.toInt()
        }

        healthAttachmentService.findByAccount(account).map { it.fileSize }.forEach {
            if (it != null)
                total += it.toInt()
        }

        if ((total / 1024 / 1024) > 100)
            allow = false

        return allow
    }
}