package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.MessageCategory
import org.springframework.data.repository.CrudRepository

interface MessageCategoryRepository: CrudRepository<MessageCategory, Long> {


}