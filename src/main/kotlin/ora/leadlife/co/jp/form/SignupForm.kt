package ora.leadlife.co.jp.form

import ora.leadlife.co.jp.config.AuthenticationMethod
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Size

/**
 * サインアップフォーム
 */
data class SignupForm(
        @get: NotBlank
        @get:Size(max = 50)
        var name: String = "",
        @get: NotBlank
        @get: Email
        @get:Size(max = 254)
        var email: String = "",
        var emailName: String = "",
        var emailHost: String = "",
        @get: NotBlank
        @get: Email
        @get:Size(max = 254)
        var emailConfirm: String = "",
        var emailConfirmName: String = "",
        var emailConfirmHost: String = "",
        @get: NotBlank
        @get:Size(max = 12, min = 8)
        var password: String = "",
        @get: NotBlank
        @get:Size(max = 12, min = 8)
        var passwordConfirm: String = "",
        @get:Size(max = 254)
        var shopCode: String = "",
        var authenticationMethodPassword: Boolean = false,
        var authenticationMethodFingerprint: Boolean = false,
        @get:AssertTrue(message = "{agreementToTerms.true}")
        var agreementToTerms: Boolean = false
) {

    @AssertTrue(message = "{checked.one}")
    fun isOneCheckedAuthentication(): Boolean {
        return (authenticationMethodFingerprint && !authenticationMethodPassword) || (!authenticationMethodFingerprint && authenticationMethodPassword)
    }

    @AssertTrue(message = "{not.equal.email}")
    fun isEqualEmail(): Boolean {
        return email == emailConfirm
    }

    @AssertTrue(message = "{not.equal.password}")
    fun isEqualPassword(): Boolean {
        return password == passwordConfirm
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