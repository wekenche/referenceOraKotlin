package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.AutomaticWithdrawals
import org.springframework.data.repository.CrudRepository

interface AutomaticWithdrawalsRepository : CrudRepository<AutomaticWithdrawals, Long> {
//    fun findByAccount(account : Account): List<Account>
}