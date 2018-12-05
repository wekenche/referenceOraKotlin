package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class EmailForm(

        @get: NotBlank
        @get: Size(max = 254)
        var email: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var password: String = ""
)