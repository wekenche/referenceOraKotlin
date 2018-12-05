package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.CashForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Cash
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.CashRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CashService {

    @Autowired
    lateinit var cashRepository: CashRepository

    fun findByAccount(account: Account) : List<Cash> = cashRepository.findByAccount(account)

    @Transactional
    fun save(cashForm:List<CashForm>?, user: User) {
        cashRepository.deleteByAccount(user.lastUsedAccount!!)
        val account = user.lastUsedAccount!!
        cashForm!!.forEach {
            cashRepository.save(Cash(amount = it.amount!! , storageLocation = it.storageLocation!!, message = it.message!! , account = account ))
        }
    }
}