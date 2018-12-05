package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.config.AuthenticationMethod
import ora.leadlife.co.jp.model.Shop
import ora.leadlife.co.jp.repository.ShopRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var shopRepository: ShopRepository


    @Test
    fun registry() {
        userService.deleteByEmail("admin@mame.com")
        userService.deleteByEmail("admin@admin.com")
        userService.registryAdmin("admin", "admin@admin.com", password = "admin",os = "OTHER")
        userService.deleteByEmail("user@user.com")
        shopRepository.deleteAll()
        val shop = Shop(name = "test", code = "mame")
        shopRepository.save(shop)
        val password = "user1234"
        userService.registryUser("user", "user@user.com", password = password, shopCode = shop.code, authenticationMethod = AuthenticationMethod.PASSWORD.name, os = "OTHER")
        val user = userService.loadUserByUsername("user@user.com")
        assert(user.shop!!.code == shop.code)
        assert(userService.match(password, user))
        assert(user.lastUsedAccount != null)
    }
}