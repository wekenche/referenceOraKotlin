package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class DepositForm (
    @get: NotBlank
    @get:Size(max = 254)
    var insuranceCompany : String? = "",
    @get: NotBlank
    @get:Size(max = 254)
    var symbolNo : String? = "",
    @get: NotBlank
    @get:Size(max = 254)
    var securityNo : String? = "",
    @get: NotBlank
    @get:Size(max = 254)
    var memo : String? = "",

    var depositWrapper: List<DepositForm>? = null
)