package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.PensionsForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Pensions
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.PensionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PensionsService {

    fun findByAccount(account: Account): List<Pensions> = pensionsRepository.findByAccount(account)


    @Autowired
    lateinit var pensionsRepository: PensionsRepository

    @Transactional
    fun save(pensionsForm: List<PensionsForm>, user: User) {
        pensionsRepository.deleteByAccount(user.lastUsedAccount!!)
        var account = user.lastUsedAccount!!
        pensionsForm.forEach() {
            pensionsRepository.save(Pensions(pensionNo = it.pensionNo, pensionType = it.pensionType, privatePension = it.privatePension, contactAddress = it.contactAddress, memo = it.memo, account = account))
        }
    }

}