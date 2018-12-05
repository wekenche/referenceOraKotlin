package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * パスワードリマインダーフォーム
 */
data class PasswordReminderForm(
        @get: NotBlank
        @get: Email
        @get:Size(max = 254)
        var email: String = ""
)