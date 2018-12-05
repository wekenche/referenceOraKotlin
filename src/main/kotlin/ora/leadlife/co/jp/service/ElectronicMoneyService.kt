package ora.leadlife.co.jp.service


import ora.leadlife.co.jp.form.ElectronicMoneyForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.ElectronicMoney
import ora.leadlife.co.jp.model.IndividualNoteCategory
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.ElectronicMoneyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ElectronicMoneyService {


    @Autowired
    lateinit var electronicMoneyRepository: ElectronicMoneyRepository

    fun findByAccount(account: Account) : List<ElectronicMoney> = electronicMoneyRepository.findByAccount(account)

    @Transactional
    fun save(electronicMoneyForm: List<ElectronicMoneyForm>?, user: User) {
        electronicMoneyRepository.deleteByAccount(user.lastUsedAccount!!)
        var account = user.lastUsedAccount!!
            electronicMoneyForm!!.forEach {
            electronicMoneyRepository.save(ElectronicMoney(name = it.name , no = it.no, certificateCompany = it.certificateCompany ,account = account ))
        }
    }
}