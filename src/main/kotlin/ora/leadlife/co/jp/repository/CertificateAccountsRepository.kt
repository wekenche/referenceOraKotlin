package ora.leadlife.co.jp.repository


import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.CertificateAccounts
import org.springframework.data.repository.CrudRepository

interface CertificateAccountsRepository : CrudRepository<CertificateAccounts,Long>{
    fun findByAccount(account : Account): List<CertificateAccounts>
    fun deleteByAccount(account: Account)
}