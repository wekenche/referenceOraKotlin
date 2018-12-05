package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Treasures
import org.springframework.data.repository.CrudRepository

interface TreasuresRepository : CrudRepository<Treasures,Long>{
    fun findByAccount(account: Account): List<Treasures>
    fun deleteByAccount(account: Account)
}