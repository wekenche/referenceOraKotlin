package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Size

/**
 * パスワードリマインダーフォーム
 */
data class PasswordForm(
        @get: NotBlank
        @get:Size(max = 12, min = 8)
        var password: String = "",
        @get: NotBlank
        @get:Size(max = 12, min = 8)
        var passwordConfirm: String = "",
        @get: NotBlank
        var resetPasswordToken: String = ""
) {
    @AssertTrue(message = "{not.equal.password}")
    fun isEqualPassword(): Boolean {
        return password == passwordConfirm
    }
}