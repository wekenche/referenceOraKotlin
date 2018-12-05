package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Addresses
import org.springframework.data.repository.CrudRepository

interface AddressesRepository : CrudRepository<Addresses, Long> {
    fun findByAccount(account : Account): List<Addresses>
    fun deleteByAccount(account : Account)
}