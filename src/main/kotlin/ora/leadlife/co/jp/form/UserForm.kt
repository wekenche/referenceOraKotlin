package ora.leadlife.co.jp.form

import ora.leadlife.co.jp.config.AuthenticationMethod
import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Size
import org.hibernate.validator.constraints.Email

data class UserForm(
        @get:Size(max = 50)
        var username: String? = null,
        @get: NotBlank
        @get: Email
        @get:Size(max = 254)
        var email: String = "",
        var oldPassword: String? = null,

        var newPassword: String? = null,
        var newPasswordConfirm: String? = null,

        var authenticationMethodPassword: Boolean = false,
        var authenticationMethodFingerprint: Boolean = false
) {

    @AssertTrue(message = "{checked.one}")
    fun isOneCheckedAuthentication(): Boolean {
        return (authenticationMethodFingerprint && !authenticationMethodPassword) || (!authenticationMethodFingerprint && authenticationMethodPassword)
    }

    /**
     * 認証方法を返します
     */
    fun getAuthenticationMethod(): String {
        if (authenticationMethodFingerprint)
            return AuthenticationMethod.FINGERPRINT.name
        return AuthenticationMethod.PASSWORD.name
    }
}