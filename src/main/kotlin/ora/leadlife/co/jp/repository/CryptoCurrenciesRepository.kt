package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.CryptoCurrencies
import org.springframework.data.repository.CrudRepository

interface CryptoCurrenciesRepository : CrudRepository<CryptoCurrencies, Long> {
    fun findByAccount(account: Account): List<CryptoCurrencies>
    fun deleteByAccount(account: Account)
}