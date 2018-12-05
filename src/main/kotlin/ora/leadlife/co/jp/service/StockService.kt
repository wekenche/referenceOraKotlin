package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.StockForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Stock
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.StockRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class StockService {

    @Autowired
    lateinit var stockRepository: StockRepository

    fun findByAccount(account: Account) : List<Stock> = stockRepository.findByAccount(account)

    @Transactional
    fun save(list : List<StockForm>, user: User) {
        val acc = user.lastUsedAccount!!
        if (stockRepository.findByAccount(acc).isNotEmpty()) {
            stockRepository.deleteByAccount(acc)
        }
        list.forEach {
            stockRepository.save(Stock(certificateCompany = it.company, brand = it.brand, certificateNo = it.number,
                    quantity = it.quantity, isCustomManagement = it.isCustomManagement, memo = it.remarks, account = acc))
        }
    }
}