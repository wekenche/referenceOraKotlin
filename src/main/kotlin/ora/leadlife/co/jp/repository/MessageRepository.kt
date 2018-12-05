package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Message
import ora.leadlife.co.jp.model.User
import org.springframework.data.repository.CrudRepository

interface MessageRepository: CrudRepository<Message, Long> {
    fun findByAccount (account: Account) : List<Message>
    fun findByAccountOrderByUpdatedAtDesc (account: Account) : List<Message>
    fun findById (id: Long) : Message
    fun deleteByAccount(account : Account)
}