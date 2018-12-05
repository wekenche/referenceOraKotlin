package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Emails
import org.springframework.data.repository.CrudRepository

interface EmailsRepository:CrudRepository<Emails,Long>{
    fun findByAccount(account : Account): List<Emails>
    fun deleteByAccount(account: Account)
}