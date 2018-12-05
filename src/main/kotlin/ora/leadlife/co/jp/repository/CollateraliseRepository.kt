package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Collateralise
import ora.leadlife.co.jp.model.Loan
import org.springframework.data.repository.CrudRepository

interface CollateraliseRepository : CrudRepository<Collateralise, Long> {
    fun findByLoan(loan : Loan): List<Collateralise>
    fun save(collateralise: Collateralise): Collateralise
}