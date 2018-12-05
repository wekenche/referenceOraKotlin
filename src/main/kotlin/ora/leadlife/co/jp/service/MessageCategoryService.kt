package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Message
import ora.leadlife.co.jp.model.MessageCategory
import ora.leadlife.co.jp.repository.MessageCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageCategoryService {
    @Autowired
    lateinit var messageCategoryRepository: MessageCategoryRepository

    fun findOne (id:Long) : MessageCategory {
        return messageCategoryRepository.findOne(id)
    }

    fun findAll () : Iterable<MessageCategory> {
        return messageCategoryRepository.findAll()
    }
}