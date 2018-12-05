package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Cash
import org.springframework.data.repository.CrudRepository

interface CashRepository:CrudRepository<Cash, Long> {
    fun findByAccount(account : Account): List<Cash>
    fun deleteByAccount(account:Account)
}