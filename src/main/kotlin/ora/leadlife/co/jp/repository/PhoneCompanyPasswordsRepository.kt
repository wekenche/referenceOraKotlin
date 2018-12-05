package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.PhoneCompanyPasswords
import org.springframework.data.repository.CrudRepository

interface PhoneCompanyPasswordsRepository:CrudRepository<PhoneCompanyPasswords,Long>{
    fun findFirstByAccountOrderByIdAsc(account : Account): PhoneCompanyPasswords
    fun deleteByAccount(account: Account)
}