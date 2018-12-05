package ora.leadlife.co.jp.helper

import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class UploadedImage {
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

    fun build(target: String?): String? {

        if (target == null) {
            return null
        }

        return target.replace("static", "")
    }

    fun totalMB(user: User): Int {
        var total: Int = 0

        messageAttachmentService.findByMessageIn(
                messageService.findByAccount(user.lastUsedAccount!!)
        ).map { it.fileSize }.forEach {
            if (it != null)
                total += it.toInt()
        }

        individualNoteAttachmentService.findByIndividualNoteIn(
                individualNoteService.findByAccount(user.lastUsedAccount!!)
        ).map { it.fileSize }.forEach {
            if (it != null)
                total += it.toInt()
        }

        healthAttachmentService.findByAccount(user.lastUsedAccount!!).map { it.fileSize }.forEach {
            if (it != null)
                total += it.toInt()
        }

        return total / 1024 / 1024
    }
}