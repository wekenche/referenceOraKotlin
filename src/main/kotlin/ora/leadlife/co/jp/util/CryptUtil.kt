package ora.leadlife.co.jp.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*


object CryptUtil {
    private val privateKey = "leadlife"
    val logger: Logger = LoggerFactory.getLogger(CryptUtil::class.java)

    fun encrypt(key: String, text: String): String {
        logger.info(text)
        return Base64.getEncoder().encodeToString(text.toByteArray())
    }

    fun decrypt(key: String, encrypted: String): String {
        logger.info(encrypted)
        return String(Base64.getDecoder().decode(encrypted))
    }

    fun encrypt(text: String): String {
        logger.info(text)
        return URLEncoder.encode(CryptUtil.encrypt(privateKey, text),"UTF-8")
    }

    fun decrypt(encrypted: String): String {
        logger.info(encrypted)
        return CryptUtil.decrypt(privateKey, URLDecoder.decode(encrypted,"UTF-8"))
    }
}
