package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByUser(user: User): List<Account>
    fun findOneByUser(user: User): Account

    @Query("SELECT a FROM Account a JOIN a.user u WHERE u.id = a.userId " +
            "AND a.isPremium = true AND u.os <> 'IOS' ORDER BY a.id")
    fun findAccountsNotIOS(): List<Account>

    @Query("SELECT a FROM Account a WHERE a.isPremium = false " +
            "AND a.premiumPayLastDate BETWEEN :startDate AND :endDate ORDER BY a.id")
    fun findAllFreeAccounts(@Param("startDate") startDate: Date,
                            @Param("endDate") endDate: Date): List<Account>
}