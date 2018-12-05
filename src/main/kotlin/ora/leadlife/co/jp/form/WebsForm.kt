package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size
import kotlin.math.max

data class WebsForm(
        @get: NotBlank
        @get: Size(max = 254)
        var name: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var webId: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var password: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var remarks: String? = ""
)