package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Stock
import org.springframework.data.repository.CrudRepository

interface StockRepository : CrudRepository<Stock, Long> {
    fun findByAccount(account : Account): List<Stock>
    fun deleteByAccount(account: Account)
}