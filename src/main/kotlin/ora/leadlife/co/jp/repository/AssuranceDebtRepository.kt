package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.AssuranceDebt
import org.springframework.data.repository.CrudRepository

interface AssuranceDebtRepository : CrudRepository<AssuranceDebt, Long> {
    fun findByAccount(account : Account): List<AssuranceDebt>
    fun deleteByAccount(account:Account)
}