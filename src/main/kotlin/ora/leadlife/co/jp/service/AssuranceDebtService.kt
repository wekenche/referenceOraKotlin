package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.AssuranceDebtForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.AssuranceDebt
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.AssuranceDebtRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional
import java.text.SimpleDateFormat



@Service
class AssuranceDebtService {


    @Autowired
    lateinit var assuranceDebtRepository: AssuranceDebtRepository

    fun findByAccount(account: Account) : List<AssuranceDebt> = assuranceDebtRepository.findByAccount(account)

    @Transactional
    fun save(assuranceDebtForm: List<AssuranceDebtForm>?, user: User) {
        assuranceDebtRepository.deleteByAccount(user.lastUsedAccount!!)
        var account = user.lastUsedAccount!!
        assuranceDebtForm!!.forEach {
            if(it.year!="" && it.month!="" && it.day!="") {
                var sampleDate = it.year + "-" + it.month + "-" + it.day
                val date1 = SimpleDateFormat("yyyy-MM-dd").parse(sampleDate)
                it.assuranceDate = date1
            }
            assuranceDebtRepository.save(AssuranceDebt(assuranceDate = it.assuranceDate,
                    assurancePrice = it.assurancePrice,
                    assuranceTarget = it.assuranceTarget ,
                    assuranceTargetContactAddress = it.assuranceTargetContactAddress,
                    creditor = it.creditor,
                    creditorContractAddress = it.creditorContractAddress,
                    account = account ))
        }
    }
}