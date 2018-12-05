package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Testaments
import org.springframework.data.repository.CrudRepository

interface TestamentsRepository : CrudRepository<Testaments, Long> {
    fun findByAccount(account: Account): Testaments
    fun deleteByAccount(account: Account)
}