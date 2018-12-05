package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Pets
import org.springframework.data.repository.CrudRepository

interface PetsRepository: CrudRepository<Pets,Long> {
    fun findByAccount(account: Account): List<Pets>
    fun deleteByAccount(account : Account)
}