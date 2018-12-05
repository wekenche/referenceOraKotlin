package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Bereave
import ora.leadlife.co.jp.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BereaveRepository : JpaRepository<Bereave, Long> {

    fun findByAccount(account: Account): List<Bereave>

    fun findByAccount(account: Account,pageable: Pageable): Page<Bereave>

    fun findByAccountAndUser(account: Account, user: User): List<Bereave>

    fun findByUser(user: User): List<Bereave>

    @Query("SELECT CASE WHEN COUNT(b.diedDate)>0 THEN true ELSE false END FROM Bereave b WHERE b.accountId in (SELECT a.accountId FROM Bereave a WHERE a.diedDate IS NULL AND a.userId = :userId)")
    fun checkDieConfirm(@Param("userId") userId:Long):Boolean

    fun deleteByAccount(account : Account)
}