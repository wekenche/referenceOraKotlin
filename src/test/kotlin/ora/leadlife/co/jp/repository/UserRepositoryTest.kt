package ora.leadlife.co.jp.repository

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest() {
    @Autowired
    val userRepository: UserRepository? = null

    @Test
    fun hoge() {
//        val user: User = User()
//        userRepository!!.save(user)
    }
}