package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class InsurancePetForm (
        @get:NotBlank
        @get: Size(max = 254)
        var company: String? = null,
        @get:NotBlank
        @get: Size(max = 254)
        var tel: String? = null,
        @get:NotBlank
        @get: Size(max = 254)
        var billingMethod: String? = null,
        @get:NotBlank
        @get: Size(max = 254)
        var remarks: String? = null
)