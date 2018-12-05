package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class HospitalPetsForm(
        @get:NotBlank
        @get: Size(max = 254)
        var diseaseName: String? = null,

        @get:NotBlank
        @get: Size(max = 254)
        var hospital: String? = null,

        @get:NotBlank
        @get: Size(max = 254)
        var contactAddress: String? = null
)