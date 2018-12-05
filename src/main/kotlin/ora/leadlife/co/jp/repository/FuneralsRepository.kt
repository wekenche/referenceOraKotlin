package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Funerals
import org.springframework.data.repository.CrudRepository

interface FuneralsRepository : CrudRepository<Funerals, Long> {
    fun findByAccount(account: Account): List<Funerals>
    fun deleteByAccount(account : Account)
}