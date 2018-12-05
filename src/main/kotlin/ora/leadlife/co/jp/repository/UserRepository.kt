package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Shop
import ora.leadlife.co.jp.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?

    @Query("SELECT t FROM User t ORDER BY t.id DESC")
    fun findAll(pageable: Pageable): Page<User>

    @Query("SELECT t FROM User t WHERE t.name LIKE %:keyword% OR t.email LIKE %:keyword% ORDER BY t.id DESC")
    fun findByKeyword(pageable: Pageable, @Param("keyword") keyword: String?): Page<User>

    @Query("SELECT DISTINCT u FROM User u " +
            "INNER JOIN u.accountList a " +
            "LEFT JOIN u.shop s " +
            "WHERE u.name LIKE %:name% AND  u.email LIKE %:email% AND coalesce(s.code,'') LIKE %:code% AND u.userType = :userType AND a.isPremium = :paid ")
    fun findByParams(pageable: Pageable, @Param("name") name: String?, @Param("email") email: String?,
                     @Param("code") code: String?, @Param("userType") userType: String?,
                     @Param("paid") paid: Boolean?): Page<User>

    @Modifying
    @Query("UPDATE User u set u.shop_id = :shop_id WHERE u.id = :user_id")
    fun updateUserShop(@Param("shop_id") shop_id : Long?,@Param("user_id") user_id : Long?): Int

    fun findByShop(shop : Shop) : List<User>
    fun existsByLastUsedAccount(account: Account): Boolean
}