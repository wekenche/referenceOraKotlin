package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Deposit
import org.springframework.data.repository.CrudRepository

interface DepositRepository  : CrudRepository<Deposit, Long> {
    fun findByAccount(account : Account): List<Deposit>
    fun deleteByAccount(account : Account)
}