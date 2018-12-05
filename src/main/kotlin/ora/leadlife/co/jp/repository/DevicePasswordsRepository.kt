package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.DevicePasswords
import org.springframework.data.repository.CrudRepository

interface DevicePasswordsRepository: CrudRepository<DevicePasswords,Long> {
    fun findByAccount(account : Account): List<DevicePasswords>
    fun deleteByAccount(account: Account)
}