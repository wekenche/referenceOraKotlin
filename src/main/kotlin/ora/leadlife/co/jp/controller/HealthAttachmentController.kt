package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.service.HealthAttachmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class HealthAttachmentController {
    @Autowired
    lateinit var healthAttachmentService: HealthAttachmentService

    @DeleteMapping("/medicine/attachment/delete/{id}")
    fun deleteHealthAttachment(@PathVariable id: String): MutableMap<String, String>? {
        healthAttachmentService.delete(id.toLong())
        return Collections.singletonMap("result","ok")
    }
}