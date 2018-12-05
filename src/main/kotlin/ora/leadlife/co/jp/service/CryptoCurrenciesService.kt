package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.CryptoCurrenciesForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.CryptoCurrencies
import ora.leadlife.co.jp.repository.AccountRepository
import ora.leadlife.co.jp.repository.CryptoCurrenciesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CryptoCurrenciesService {
    @Autowired
    lateinit var cryptoCurrenciesRepository: CryptoCurrenciesRepository

    fun findByAccount(account: Account) =  cryptoCurrenciesRepository.findByAccount(account)
    @Transactional
    fun save(cryptoCurrenciesForm: CryptoCurrenciesForm, account: Account) {
        if(findByAccount(account).isNotEmpty()){
            cryptoCurrenciesRepository.deleteByAccount(account)
        }
        var list: MutableList<CryptoCurrencies> = mutableListOf()
        cryptoCurrenciesForm.cryptoCurrenciesWrapper!!.forEach {
            var c = CryptoCurrencies(market = it.market, cryptoCurrencyType = it.cryptoCurrencyType,
                    amount = it.amount, cryptoCurrencyId = it.cryptoCurrencyId,
                    password = it.password, memo = it.remarks, account = account)
            list.add(c)
        }
        cryptoCurrenciesRepository.save(list)
    }
}