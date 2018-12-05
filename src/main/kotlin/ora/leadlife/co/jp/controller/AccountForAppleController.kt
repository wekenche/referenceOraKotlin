package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AccountForAppleController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var accountService: AccountService

    val logger: Logger = LoggerFactory.getLogger(AccountForAppleController::class.java)

    @GetMapping("/accountForApple")
    fun account(@AuthenticationPrincipal user: User): MutableMap<String, Long?>? {
        return Collections.singletonMap("result", user.lastUsedAccount!!.id)
    }

    @GetMapping("/accountForApple/unused/{id}")
    fun unused(@PathVariable id: String): MutableMap<String, String>? {
        val result = userService.getNameOfApplePay(id)
        return Collections.singletonMap("result", result.first + result.second)
    }
}