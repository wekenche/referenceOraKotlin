package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

/**
* アカウント作成フォーム
*/
data class AccountForm(
        @get: NotBlank
        @get:Size(max = 50)
        var name: String = ""
)
