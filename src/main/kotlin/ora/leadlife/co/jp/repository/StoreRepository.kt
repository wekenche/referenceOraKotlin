package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Store
import org.springframework.data.repository.CrudRepository

interface StoreRepository  : CrudRepository<Store, Long> {
    fun findByAccount(account : Account): List<Store>
    fun deleteByAccount(account: Account)
}