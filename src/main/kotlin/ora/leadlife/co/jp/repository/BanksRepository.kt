package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Banks
import org.springframework.data.repository.CrudRepository

interface BanksRepository: CrudRepository<Banks, Long> {
    fun findByAccount(account : Account): List<Banks>
    fun deleteByAccount(account : Account)
}