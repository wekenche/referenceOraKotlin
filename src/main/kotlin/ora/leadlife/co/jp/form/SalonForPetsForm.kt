package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class SalonForPetsForm (
        @get: NotBlank
        @get: Size(max=254)
        var name: String? = null,
        @get: NotBlank
        @get: Size(max=254)
        var contactAddress: String? = null
)