package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.FinancialAsset
import org.springframework.data.repository.CrudRepository

interface FinancialAssetRepository : CrudRepository<FinancialAsset, Long> {
    fun findByAccount(account : Account): List<FinancialAsset>

    fun deleteByAccount(account:Account)
}