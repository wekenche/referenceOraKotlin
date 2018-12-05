package ora.leadlife.co.jp.service
import ora.leadlife.co.jp.form.FinancialAssetForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.FinancialAsset
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.FinancialAssetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class FinancialAssetService {

    @Autowired
    lateinit var financialAssetRepository: FinancialAssetRepository

    fun findByAccount(account: Account) : List<FinancialAsset> = financialAssetRepository.findByAccount(account)
    @Transactional
    fun save (pensionsForm: List<FinancialAssetForm>, user: User) {
        financialAssetRepository.deleteByAccount(user.lastUsedAccount!!)
        var account = user.lastUsedAccount!!
        pensionsForm.forEach(){
            financialAssetRepository.save(FinancialAsset(name = it.name, holder = it.holder, company = it.company, symbolNo = it.symbolNo,contactAddress=it.contactAddress, memo = it.memo, account = account))
        }
    }

}