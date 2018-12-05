package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.CreditCardForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.CreditCard
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.CreditCardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CreditCardService {
    @Autowired
    lateinit var creditCardRepository: CreditCardRepository

    fun findByAccount(account: Account) : List<CreditCard> = creditCardRepository.findByAccount(account)

    @Transactional
    fun save(list: CreditCardForm, user: User) {
        val acc = user.lastUsedAccount!!
        if(creditCardRepository.findByAccount(acc).isNotEmpty()) {
            creditCardRepository.deleteByAccount(acc)
        }
        list.creditCardWrapper!!.forEach {
            creditCardRepository.save(CreditCard(accountId = acc.id!!, account = acc, name = it.creditCardName, brand = it.creditCardBrand, cardNo = it.creditCardNumber, contactAddress = it.contactInfo, webId = it.web, memo = it.remarks))
        }
    }
}