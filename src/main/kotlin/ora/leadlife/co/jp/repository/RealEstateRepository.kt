package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.RealEstate
import org.springframework.data.repository.CrudRepository

interface RealEstateRepository:CrudRepository<RealEstate,Long> {
    fun findByAccount(account : Account): List<RealEstate>
    fun deleteByAccount(account : Account)
}