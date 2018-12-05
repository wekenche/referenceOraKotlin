package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Graves
import org.springframework.data.repository.CrudRepository


interface GravesRepository : CrudRepository<Graves, Long> {
    fun findByAccountId(accountId: Long): Graves
    fun deleteByAccount(account: Account)
}

