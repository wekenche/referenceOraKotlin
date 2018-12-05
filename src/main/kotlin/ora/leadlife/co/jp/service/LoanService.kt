package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.LoanForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Collateralise
import ora.leadlife.co.jp.model.Loan
import ora.leadlife.co.jp.repository.CollateraliseRepository
import ora.leadlife.co.jp.repository.LoanRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class LoanService {

    @Autowired
    lateinit var loanRepository: LoanRepository

    @Autowired
    lateinit var collateraliseRepository: CollateraliseRepository

    fun findByAccount (account: Account) : List<Loan>{
        return loanRepository.findByAccount(account)
    }
    @Transactional
    fun save(loanForm: LoanForm, account: Account) {
        var loan: Loan
        loanForm.loanFormWrapper!!.forEach {
            loan = loanRepository.save(
                    Loan(
                            id = it.loanId!!,
                            loanTarget = it.target,
                            tel = it.tel,
                            postCode1 = it.postCode1,
                            postCode2 = it.postCode2,
                            address = it.address,
                            loanDate = Date(it.dateY.toInt(),it.dateM.toInt(),it.dateD.toInt()),
                            loanAmount = it.amount,
                            refundMethod = it.refundMethod,
                            loanBalance = it.balance,
                            loanPurpose = it.purpose,
                            account = account
                    )
            )
            it.collateraliseFormWrapper!!.forEach{
                collateraliseRepository.save(
                    Collateralise(
                            id = it.collateralId!!,
                            name = it.name,
                            loan = loan
                    )
                )
            }
        }
    }

    @Transactional
    fun delete(id: Long) {
        loanRepository.delete(id)
    }
}