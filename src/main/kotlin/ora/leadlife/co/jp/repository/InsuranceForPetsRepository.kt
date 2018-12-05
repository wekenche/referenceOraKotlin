package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.InsuranceForPets
import org.springframework.data.repository.CrudRepository

interface InsuranceForPetsRepository : CrudRepository<InsuranceForPets, Long> {
    fun findByAccount(account: Account): List<InsuranceForPets>
    fun deleteByAccount(account: Account)
}