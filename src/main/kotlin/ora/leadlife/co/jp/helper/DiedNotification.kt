package ora.leadlife.co.jp.helper

import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.BereaveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DiedNotification {
    @Autowired
    lateinit var bereaveService: BereaveService

    fun isPremium(user: User): Boolean {
        return user.lastUsedAccount!!.isPremium
    }
    fun checkOtherDiedDate(user: User): Boolean {
        return bereaveService.checkDieConfirm(user.id!!)
    }
}