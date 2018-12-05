package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.AddressForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Addresses
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.AddressesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AddressService {
    @Autowired lateinit var addressesRepository : AddressesRepository

    fun findByAccount(account : Account) : List<Addresses> = addressesRepository.findByAccount(account)

    @Transactional
    fun save(addressForm: AddressForm, user: User) {
        val account = user.lastUsedAccount!!
        if(addressesRepository.findByAccount(account).isNotEmpty()) {
            addressesRepository.deleteByAccount(account)
        }
        addressForm.addWrapper!!.forEach {
            addressesRepository.save(Addresses(postCode1 = it.postCode1 , postCode2 = it.postCode2, name = it.address, account = account))
        }
    }
}