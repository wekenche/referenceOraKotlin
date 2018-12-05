package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.HealthAttachment
import ora.leadlife.co.jp.repository.HealthAttachmentRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File

@Service
class HealthAttachmentService {
    @Autowired
    lateinit var healthAttachmentRepository:HealthAttachmentRepository

    val logger: Logger = LoggerFactory.getLogger(HealthAttachmentService::class.java)

    fun findByAccount(account: Account):List<HealthAttachment>{
        return healthAttachmentRepository.findByAccount(account)
    }

    @Transactional
    fun save(account: Account,healthAttachment:List<HealthAttachment>){
        healthAttachmentRepository.save(healthAttachment)
    }

    @Transactional
    fun delete(id: Long) {
        val healthAttachment = healthAttachmentRepository.findOne(id)
        try {
            val file = File(healthAttachment.filePath)
            if (file.exists())
                file.delete()
        } catch (e: Exception) {
            logger.error(e.localizedMessage)
        }
        healthAttachmentRepository.delete(id)
    }
}