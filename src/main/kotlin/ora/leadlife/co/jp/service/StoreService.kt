package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.StoreForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Store
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class StoreService {

    @Autowired lateinit var storeRepository: StoreRepository

    fun findByAccount(account : Account) : List<Store> = storeRepository.findByAccount(account)

    @Transactional
    fun save(storeForm: StoreForm, user: User) {
        var account = user.lastUsedAccount!!
        if(findByAccount(account).isNotEmpty()) {
            storeRepository.deleteByAccount(account)
        }
        storeForm.storeWrapper!!.forEach {
        storeRepository.save(Store(contractCompany = it.companyName, contractAddress = it.contact , postCode1 = it.postCode1,
                postCode2 = it.postCode2, address = it.address ,memo = it.memo, account = account))
        }
    }
}