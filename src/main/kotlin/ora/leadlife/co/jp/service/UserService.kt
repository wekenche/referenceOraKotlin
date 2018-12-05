package ora.leadlife.co.jp.service

import com.sun.xml.internal.fastinfoset.stax.events.Util
import ora.leadlife.co.jp.config.AuthenticationMethod
import ora.leadlife.co.jp.config.Role
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.IndividualNoteCategory
import ora.leadlife.co.jp.model.Shop
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.IndividualNoteCategoryRepository
import ora.leadlife.co.jp.repository.IndividualNoteCategoryTemplatesRepository
import ora.leadlife.co.jp.repository.ShopRepository
import ora.leadlife.co.jp.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * ユーザー関連サービス
 */
@Service
class UserService() : UserDetailsService {

    /** ユーザーレポジトリ */
    @Autowired
    lateinit var userRepository: UserRepository

    /** アカウントレポジトリ*/

    /** 店舗リポジトリ*/
    @Autowired
    lateinit var shopRepository: ShopRepository

    @Autowired
    lateinit var individualNoteCategoryTemplatesRepository: IndividualNoteCategoryTemplatesRepository

    @Autowired
    lateinit var individualNoteCategoryRepository: IndividualNoteCategoryRepository

    @Autowired
    lateinit var accountService: AccountService

    val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    /**
     * Spring Securityで使用します
     */
    override fun loadUserByUsername(p0: String?): User {
        if (p0 == null)
            throw UsernameNotFoundException("$p0 is not found")
        return userRepository.findByEmail(p0) ?: throw UsernameNotFoundException("$p0 is not found")
    }

    /**
     * 管理者ユーザーを追加
     *
     * @param name ログイン名
     * @param email メールアドレス
     * @param password パスワード
     */
    fun registryAdmin(name: String, email: String, password: String, os: String) {
        userRepository.save(makeUser(name = name, email = email, password = password, os = os,userType = Role.ADMIN.name))
    }

    /**
     * 一般ユーザーを追加
     *
     * @param name ログイン名
     * @param email メールアドレス
     * @param password パスワード
     */
    @Transactional
    fun registryUser(name: String, email: String, password: String, shopCode: String? = null, authenticationMethod: String, os : String): User {
        val user = userRepository.save(
                makeUser(name = name, email = email, password = password, userType = Role.USER.name, shopCode = shopCode, os = os)
        )
        val account = Account(user = user, name = "初期作成")
//        accountRepository.save(account)
        user.lastUsedAccount = account
        userRepository.save(user)
        user.accountList = mutableListOf()
        user.accountList!!.add(account)

        individualNoteCategoryTemplatesRepository.findAll().forEach {
            individualNoteCategoryRepository.save(IndividualNoteCategory(name = it.name , individualNoteCategoryTemplate = it, individualNoteCategoryTemplateId = it.id ,account = account, accountId = account.id ))
        }
        return user
    }

    /**
     * メールアドレスでユーザーを削除
     *
     * @param email メールアドレス
     */
    fun deleteByEmail(email: String) {
        val user = userRepository.findByEmail(email)
        if (user != null)
            userRepository.delete(user)
    }

    /**
     * ユーザーを作成
     */
    private fun makeUser(name: String, email: String, password: String, userType: String, shopCode: String? = null, os : String,authenticationMethod: String = AuthenticationMethod.PASSWORD.name): User {
        val shop = shopCode?.let { shopRepository.findByCode(it) }
        return User(name = name, email = email, encryptedPassword = passwordEncoder().encode(password), userType = userType, shop = shop, os = os,authenticationMethod = authenticationMethod)
    }

    /**
     * パスワードエンコーダーを返す
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * パスワードがマッチするか確認します。
     */
    fun match(rawPassword: String, user: User): Boolean {
        val tmp = findByEmail(user.email)
        return passwordEncoder().matches(rawPassword, tmp!!.password)
    }

    /**
     * メールアドレスで検索
     * @param email メールアドレス
     */
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun findById(id: Long):User {
        return userRepository.findOne(id)
    }

    fun findByShop(shop : Shop) : List<User> {
        return userRepository.findByShop(shop)
    }

    /**
     * 保存
     */
    @Transactional
    fun save(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    fun updateUserShop(shop_id : Long? , id : Long) : Int? {
        return userRepository.updateUserShop(shop_id,id)
    }

    fun findByParams(pageable: Pageable, name : String?, email : String?, code : String?,paid : Boolean?, userType : String?) : Page<User> {
        return userRepository.findByParams(pageable, name,email,code,userType,paid)
    }

    fun getAll(pageable: Pageable, keyword: String? = null): Page<User> {
        return if (Util.isEmptyString(keyword))
            userRepository.findAll(pageable)
        else
            userRepository.findByKeyword(pageable, keyword)
    }
    fun existsByLastUsedAccount(account: Account) = userRepository.existsByLastUsedAccount(account)

    /**
     * return name of apple pay
     */
    fun getNameOfApplePay(accountId: String): Pair<String, Int> {
        val user = accountService.findById(accountId).user
        var result = 1
        val list = mutableListOf<Int>()
        user.accountList?.forEach {
            val account = it
            account.payments?.forEach {
                if (it.appleProductId != null) {
                    try {
                        list.add(it.appleProductId!!.replace("NORMAL_", "").replace("SHOP_", "").toInt())
                    } catch (ex: RuntimeException) {
                        logger.warn(ex.localizedMessage)
                    }
                }
            }
            (10 downTo 1).forEach {
                if (!list.contains(it))
                    result = it
            }
        }
        val header = if (user.shop == null) "NORMAL_" else "SHOP_"
        return Pair(header, result)
    }
}