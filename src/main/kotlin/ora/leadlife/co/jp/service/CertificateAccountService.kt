package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.CertificateAccountForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.CertificateAccounts
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.CertificateAccountsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CertificateAccountService {

    @Autowired lateinit var certificateAccountsRepository: CertificateAccountsRepository

    fun findByAccount(account: Account) : List<CertificateAccounts> = certificateAccountsRepository.findByAccount(account)

    @Transactional
    fun save(certificateAccountForm: CertificateAccountForm, user: User) {
        val account = user.lastUsedAccount!!
        if(certificateAccountsRepository.findByAccount(account).isNotEmpty()) {
            certificateAccountsRepository.deleteByAccount(account)
        }
        certificateAccountForm.caWrapper!!.forEach {
            certificateAccountsRepository.save(CertificateAccounts(
                    name = it.caName,
                    bankAccountNumber = it.bankAccountNumber,
                    holder = it.holder,
                    webId = it.webId,
                    contactAddress = it.contactAddress,
                    memo = it.remarks,
                    account = account))
        }
    }
}