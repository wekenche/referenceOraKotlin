package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Healths
import org.springframework.data.repository.CrudRepository

interface HealthsRepository:CrudRepository<Healths,Long> {
    fun findByAccount(account : Account): Healths
    fun deleteByAccount(account : Account)
}