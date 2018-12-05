package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Loan
import org.springframework.data.repository.CrudRepository

interface LoanRepository : CrudRepository<Loan, Long> {
    fun findByAccount(account : Account): List<Loan>
    fun deleteByAccount(account: Account)
}