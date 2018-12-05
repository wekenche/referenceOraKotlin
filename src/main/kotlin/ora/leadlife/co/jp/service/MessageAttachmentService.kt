package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.MessageForm
import ora.leadlife.co.jp.model.Message
import ora.leadlife.co.jp.model.MessageAttachment
import ora.leadlife.co.jp.repository.MessageAttachmentRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import javax.transaction.Transactional

@Service
class MessageAttachmentService {
    @Autowired
    lateinit var messageService: MessageService
    @Autowired
    lateinit var messageAttachmentRepository: MessageAttachmentRepository

    val logger: Logger = LoggerFactory.getLogger(MessageAttachmentService::class.java)

    fun findByMessage(message: Message): List<MessageAttachment> {
        return messageAttachmentRepository.findByMessage(message)
    }

    fun findByMessageIn(messages: List<Message>): List<MessageAttachment> {
        return messageAttachmentRepository.findByMessageIn(messages)
    }

    /**
     * 保存
     */
    @Transactional
    fun save(messageAttachment: MessageAttachment) {
        if(messageService.findById(messageAttachment.message.id!!)!=null){
            messageAttachmentRepository.deleteByMessage(messageAttachment.message!!)
        }
         messageAttachmentRepository.save(messageAttachment)
    }

    @Transactional
    fun delete(id: Long) {
        val messageAttachment = messageAttachmentRepository.findOne(id)
        try {
            val file = File(messageAttachment.filePath)
            if (file.exists())
                file.delete()
        } catch (e: Exception) {
            logger.error(e.localizedMessage)
        }
        messageAttachmentRepository.delete(id)
    }
}