package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.AutomaticWithdrawalsForm
import ora.leadlife.co.jp.form.BankForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.AutomaticWithdrawals
import ora.leadlife.co.jp.model.Banks
import ora.leadlife.co.jp.repository.AutomaticWithdrawalsRepository
import ora.leadlife.co.jp.repository.BanksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BankService {
    @Autowired
    lateinit var banksRepository:BanksRepository
    @Autowired
    lateinit var autoWithdrawals:AutomaticWithdrawalsRepository

    fun findByAccount(account : Account)= banksRepository.findByAccount(account)
    @Transactional
    fun save(bankForm: BankForm,account:Account){
        var bank: Banks
        if(findByAccount(account).isNotEmpty()){
            banksRepository.deleteByAccount(account)
        }
        bankForm.bankFormWrapper!!.forEach {
            bank = banksRepository.save(Banks(name = it.name,branchName = it.branchName,depositType = it.depositType,bankAccountNumber = it.bankAccountNumber,holder = it.holder,webId = it.webId,bankUsage = it.bankUsage,account = account))
            it.automaticWithdrawalsWrapper!!.forEach {
                autoWithdrawals.save(AutomaticWithdrawals(expenseItem = it.expenseItem,withdrawalDate = it.withdrawalDate,memo = it.memo,bank = bank))
            }
        }
    }
}