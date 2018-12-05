package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.DepositForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Deposit
import ora.leadlife.co.jp.repository.DepositRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class DepositService {
    @Autowired
    lateinit var depositRepository: DepositRepository

    fun findByAccount(account: Account):List<Deposit>{
        return depositRepository.findByAccount(account)
    }
    @Transactional
    fun save(depositForm: DepositForm, account: Account) {
        if(depositRepository.findByAccount(account).isNotEmpty()){
            depositRepository.deleteByAccount(account)
        }
        depositForm.depositWrapper!!.forEach{
            depositRepository.save(Deposit(category = it.insuranceCompany!!,symbolNo = it.symbolNo!!,certificateNo = it.securityNo!!,memo = it.memo!!,account = account,accountId = account.id!!))
        }
    }
}