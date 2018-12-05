package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.ElectronicMoney
import org.springframework.data.repository.CrudRepository

interface ElectronicMoneyRepository: CrudRepository<ElectronicMoney, Long> {
    fun findByAccount(account : Account): List<ElectronicMoney>

    fun deleteByAccount(account:Account)
}