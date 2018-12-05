package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.FuneralLists
import org.springframework.data.repository.CrudRepository

interface FuneralListsRepository : CrudRepository<FuneralLists, Long> {
    fun findByAccount(account: Account): List<FuneralLists>
    fun deleteByAccount(account : Account)
}