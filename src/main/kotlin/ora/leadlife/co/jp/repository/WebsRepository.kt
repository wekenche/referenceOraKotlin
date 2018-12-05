package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Webs
import org.springframework.data.repository.CrudRepository

interface WebsRepository:CrudRepository<Webs,Long> {
    fun findByAccount(account : Account): List<Webs>
    fun deleteByAccount(account: Account)

}