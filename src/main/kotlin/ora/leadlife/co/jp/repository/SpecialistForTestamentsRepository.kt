package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.SpecialistForTestaments
import org.springframework.data.repository.CrudRepository

interface SpecialistForTestamentsRepository : CrudRepository<SpecialistForTestaments, Long> {
    fun findByAccount(account: Account): List<SpecialistForTestaments>
    fun deleteByAccount(account: Account)
}