package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.service.IndividualNoteAttachmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class IndividualNoteAttachmentController {
    @Autowired
    lateinit var individualNoteAttachmentService: IndividualNoteAttachmentService

    @DeleteMapping("/individualNote/attachment/delete/{id}")
    fun deleteAttachment(@PathVariable id: String): MutableMap<String, String>? {
        individualNoteAttachmentService.delete(id.toLong())
        return Collections.singletonMap("result","ok")
    }
}