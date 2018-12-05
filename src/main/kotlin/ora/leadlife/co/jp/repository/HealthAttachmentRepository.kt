package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.HealthAttachment
import org.springframework.data.repository.CrudRepository

interface HealthAttachmentRepository: CrudRepository<HealthAttachment,Long> {
    fun findByAccount(account: Account):List<HealthAttachment>

    fun deleteByAccount(account: Account)
}