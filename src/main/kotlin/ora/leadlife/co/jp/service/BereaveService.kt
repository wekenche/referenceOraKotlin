package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.ShareAccountForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Bereave
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.BereaveRepository
import ora.leadlife.co.jp.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult

@Service
class BereaveService {
    @Autowired
    lateinit var bereaveRepository: BereaveRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var accountService: AccountService

    fun findByAccount(account: Account): List<Bereave> {
        return bereaveRepository.findByAccount(account)
    }

    fun findByAccount(account: Account,pageable:Pageable): Page<Bereave> {
        return bereaveRepository.findByAccount(account,pageable)
    }

    fun add(account: Account, shareAccountForm: ShareAccountForm, bindingResult: BindingResult): Bereave? {
        val user = userRepository.findByEmail(shareAccountForm.email)
        if (user != null && bereaveRepository.findByAccountAndUser(account, user).isNotEmpty()) {
            bindingResult.rejectValue("email", "exist")
            return null
        }
        return if (user == null) {
            bindingResult.rejectValue("email", "not.exist.email")
            null
        } else {
            var bereave = Bereave(
                    account = account,
                    user = user,
                    approval = false
            )
            bereave = bereaveRepository.save(bereave)
            bereave
        }
    }

    fun delete(id: Long) = bereaveRepository.delete(id)

    fun findByUser(user: User) = bereaveRepository.findByUser(user)

    fun findById(id: Long): Bereave? = bereaveRepository.findOne(id)

    fun save(bereave: Bereave): Bereave = bereaveRepository.save(bereave)

    fun findByAccountAndUser(account: Account, user: User) = bereaveRepository.findByAccountAndUser(account, user)

    fun getOther(user: User): Bereave? {
        val bereaveList = findByAccount(user.lastUsedAccount!!)
        bereaveList.forEach {
            if (it.user.id != user.id)
                return it
        }
        return null
    }

    /**
     * 共有者かどうか
     */
    fun isBereave(fromAccountId: Long, toAccountId: Long): Boolean {
        var bereave = mutableListOf<Bereave>()
        val account : Account? = accountService.findByIdToCheckNull(fromAccountId.toString())
        if(account != null) {
            bereave = findByAccountAndUser(
                    account = accountService.findById(fromAccountId.toString()),
                    user = accountService.findById(toAccountId.toString()).user
            ).toMutableList()
        }
        return bereave.isNotEmpty()
    }

    fun checkDieConfirm(userId: Long)=bereaveRepository.checkDieConfirm(userId)
}