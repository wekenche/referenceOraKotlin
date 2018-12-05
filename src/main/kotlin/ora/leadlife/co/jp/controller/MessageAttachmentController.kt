package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.service.MessageAttachmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MessageAttachmentController {
    @Autowired
    lateinit var messageAttachmentService: MessageAttachmentService

    @DeleteMapping("/message/attachment/delete/{id}")
    fun deleteAttachment(@PathVariable id: String): MutableMap<String, String>? {
        messageAttachmentService.delete(id.toLong())
        return Collections.singletonMap("result", "ok")
    }
}