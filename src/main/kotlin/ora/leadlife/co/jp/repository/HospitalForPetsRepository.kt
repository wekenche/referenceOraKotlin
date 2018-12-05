package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.HospitalForPets
import org.springframework.data.repository.CrudRepository

interface HospitalForPetsRepository : CrudRepository<HospitalForPets, Long> {
    fun findByAccount(account: Account): List<HospitalForPets>
    fun deleteByAccount(account: Account)
}