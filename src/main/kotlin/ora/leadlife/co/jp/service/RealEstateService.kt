package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.RealEstateForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.RealEstate
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.RealEstateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RealEstateService {

    @Autowired lateinit var realEstateRepository: RealEstateRepository

    fun findByAccount(account: Account) : List<RealEstate> = realEstateRepository.findByAccount(account)

    @Transactional
    fun save(realEstateForm: RealEstateForm, user: User) {
        val account = user.lastUsedAccount!!
        if(realEstateRepository.findByAccount(account).isNotEmpty()) {
            realEstateRepository.deleteByAccount(account)
        }
        realEstateForm.estateWrapper!!.forEach {
            realEstateRepository.save(RealEstate(
                    parcelNumber = it.parcelNumber,
                    landCategory = it.landCategory ,
                    equity = it.equity,
                    address = it.address ,
                    memo = it.memo,
                    account = account))
        }
    }
}