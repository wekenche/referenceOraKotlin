package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.SalonForPets
import org.springframework.data.repository.CrudRepository

interface SalonForPetsRepository : CrudRepository<SalonForPets,Long> {
    fun findByAccount(account: Account): List<SalonForPets>
    fun deleteByAccount(account: Account)
}