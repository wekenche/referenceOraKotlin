package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Pensions
import org.springframework.data.repository.CrudRepository

interface PensionsRepository :  CrudRepository<Pensions,Long> {
    fun findByAccount(account: Account):List<Pensions>
    fun deleteByAccount(account:Account)
}