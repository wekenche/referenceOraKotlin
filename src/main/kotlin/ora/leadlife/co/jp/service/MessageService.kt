package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Message
import ora.leadlife.co.jp.repository.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MessageService {
    @Autowired
    lateinit var messageRepository: MessageRepository

    fun findByAccount(account: Account): List<Message> {
        return messageRepository.findByAccount(account)
    }

    fun findByAccountOrderByUpdatedAtDesc(account: Account): List<Message> {
        return messageRepository.findByAccountOrderByUpdatedAtDesc(account)
    }

    fun findById(id: Long): Message {
        return messageRepository.findById(id)
    }

    /**
     * 保存
     */
    @Transactional
    fun save(message: Message): Message {
        return messageRepository.save(message)
    }

    @Transactional
    fun delete(message: Message) {
        messageRepository.delete(message)
    }
}