package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Form for share account
 */
data class ShareAccountForm(
        @get: NotBlank
        @get:Size(max = 250)
        var email: String = ""
)