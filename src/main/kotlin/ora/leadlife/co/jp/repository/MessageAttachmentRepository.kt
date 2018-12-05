package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Message
import ora.leadlife.co.jp.model.MessageAttachment
import org.springframework.data.repository.CrudRepository

interface MessageAttachmentRepository: CrudRepository<MessageAttachment, Long> {
    fun findByMessage (message: Message) : List<MessageAttachment>
    fun findByMessageIn (messages: List<Message>) : List<MessageAttachment>
    fun deleteByMessage(messages:Message)
}