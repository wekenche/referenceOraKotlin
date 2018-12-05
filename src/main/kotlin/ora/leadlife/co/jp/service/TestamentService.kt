package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.TestamentForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.SpecialistForTestaments
import ora.leadlife.co.jp.model.Testaments
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.SpecialistForTestamentsRepository
import ora.leadlife.co.jp.repository.TestamentsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TestamentService {

    @Autowired lateinit var testamentsRepository : TestamentsRepository
    @Autowired lateinit var specialistForTestamentsRepository: SpecialistForTestamentsRepository

    fun findByAccount(account: Account) : Testaments? = testamentsRepository.findByAccount(account)

    @Transactional
    fun save(i: TestamentForm, user: User) {
        val account = user.lastUsedAccount!!
        testamentsRepository.save(Testaments(id = i.testId, existsTestament = i.existsTestament , testamentType = i.testamentType , storingPlace = i.storingPlace , account = account))
        if(specialistForTestamentsRepository.findByAccount(account).isNotEmpty()) {
            specialistForTestamentsRepository.deleteByAccount(account)
        }
        i.tsWrapper!!.forEach {
            specialistForTestamentsRepository.save(SpecialistForTestaments(
                    account = account,
                    name = it.name,
                    name2 = it.name2,
                    postCode1 = it.postCode1,
                    postCode2 = it.postCode2,
                    address = it.streetAddress,
                    tel = it.number
            ))
        }
    }
}