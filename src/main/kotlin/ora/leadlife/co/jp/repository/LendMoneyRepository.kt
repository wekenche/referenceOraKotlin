package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.LendMoney
import org.springframework.data.repository.CrudRepository

interface LendMoneyRepository  : CrudRepository<LendMoney, Long> {
    fun findByAccount(account : Account): List<LendMoney>
    fun deleteByAccount(account: Account)
}