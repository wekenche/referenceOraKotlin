package ora.leadlife.co.jp.util

import ora.leadlife.co.jp.config.OraConfiguration
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class Util {

    companion object {
        private val ENCRYPT_PASSWORD = "leadlife"
        private val ENCRYPT_SALT = "fc1df59c47fab333"

        /**
         * 文字列を暗号化
         */
        fun encrypt(data: String): String {
            return CryptUtil.encrypt(data)
        }

        /**
         * 文字列を複合化
         */
        fun decrypt(data: String): String {
            return CryptUtil.decrypt(data)
        }

        /**
         * ホスト名を取得
         */
        fun getHost(request: HttpServletRequest): String {
            var port = if (request.serverName == "localhost") ":" + request.serverPort else ""
            return if (request.isSecure) "https://" + request.serverName else "http://" + request.serverName + port
        }

        fun getMailHost(request: HttpServletRequest, path: String, oraConfiguration: OraConfiguration): String {
            var result = getHost(request)
            if (oraConfiguration.mailType != "http") {
                result += "/smartPhone?path="
            }
            result += path
            return result
        }
    }
}