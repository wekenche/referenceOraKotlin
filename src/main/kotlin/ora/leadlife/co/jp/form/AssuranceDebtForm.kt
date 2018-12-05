package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import java.util.*
import javax.validation.constraints.Size

data class AssuranceDebtForm (
        @get: NotBlank
        var year: String = " ",
        @get: NotBlank
        var month: String = " ",
        @get: NotBlank
        var day: String = " ",
        @get: NotBlank
        var assuranceDate: Date? = null,
        @get: NotBlank
        @get:Size(max = 254)
        var assurancePrice: String = "",
        @get: NotBlank
        @get:Size(max = 254)
        var assuranceTarget: String = "",
        @get: NotBlank
        @get:Size(max = 254)
        var assuranceTargetContactAddress: String = "",
        @get: NotBlank
        @get:Size(max = 254)
        var creditor: String = "",
        @get: NotBlank
        @get:Size(max = 254)
        var creditorContractAddress: String = "",
        var assuranceDebtWrapper: List<AssuranceDebtForm>? = null

)