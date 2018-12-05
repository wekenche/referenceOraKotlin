package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class PensionsForm (

            @get: NotBlank
            @get:Size(max = 254)
            var pensionNo: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var pensionType: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var privatePension: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var contactAddress: String = "",
            @get: NotBlank
            var memo: String = "",
            var pensionsWrapper: List<PensionsForm>? = null



    )
