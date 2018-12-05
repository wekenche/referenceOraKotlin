package ora.leadlife.co.jp.util

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class CryptUtilTest {
    @Test
    fun normal(){
        val email = "jjaji80+201807100702@gmail.com"
        val encrypted = CryptUtil.encrypt(email)
        val decrypted= CryptUtil.decrypt(encrypted)
        assert(email == decrypted)
    }
}