package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.CreditCard
import org.springframework.data.repository.CrudRepository

interface CreditCardRepository: CrudRepository<CreditCard, Long> {
    fun findByAccount(account : Account): List<CreditCard>
    fun deleteByAccount(account: Account)
}
