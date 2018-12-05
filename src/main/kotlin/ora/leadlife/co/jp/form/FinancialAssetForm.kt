package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

    data class FinancialAssetForm (

            @get: NotBlank
            @get:Size(max = 254)
            var name: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var holder: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var company: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var symbolNo: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var contactAddress: String = "",
            @get: NotBlank
            var memo: String = "",
            var financialAssetWrapper: List<FinancialAssetForm>? = null



    )



