package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.LendMoneyForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.LendMoney
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.LendMoneyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import javax.transaction.Transactional

@Service
class LendMoneyService {

    @Autowired lateinit var lendMoneyRepository : LendMoneyRepository

    fun findByAccount(account: Account) : List<LendMoney> = lendMoneyRepository.findByAccount(account)

    @Transactional
    fun save(lendMoneyForm: LendMoneyForm, user: User) {
        val account = user.lastUsedAccount!!
        if(lendMoneyRepository.findByAccount(account).isNotEmpty()) {
            lendMoneyRepository.deleteByAccount(account)
        }
        lendMoneyForm.lmWrapper!!.forEach {
            var newDate: Date? = Date()
            if(it.lendYear!="" && it.lendMonth!="" && it.lendDay!=""){
                newDate = SimpleDateFormat("yyyy-MM-dd").parse(it.lendYear + "-" + it.lendMonth + "-" + it.lendDay)
            }

            lendMoneyRepository.save(LendMoney(
                    name = it.name,
                    tel = it.tel,
                    address = it.address,
                    postCode1 = it.postCode1,
                    postCode2 = it.postCode2,
                    amount = it.amount ,
                    storingPlace = it.storingPlace,
                    lendDate = newDate!!,
                    balance = it.balance,
                    existsDeed = it.existDeed,
                    memo = it.remarks,
                    account = user.lastUsedAccount!!))
        }
    }
}