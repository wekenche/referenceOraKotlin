package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Collateralise
import ora.leadlife.co.jp.model.Loan
import ora.leadlife.co.jp.repository.CollateraliseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.TransactionScoped

@Service
class CollateraliseService {
    @Autowired
    lateinit var collateraliseRepository: CollateraliseRepository

    fun findByLoan(loan: Loan) : List<Collateralise> = collateraliseRepository.findByLoan(loan)

    @TransactionScoped
    fun save(collateralise: Collateralise) : Collateralise {
        return collateraliseRepository.save(collateralise)
    }
}