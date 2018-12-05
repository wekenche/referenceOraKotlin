package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class TreasuresForm(
        @get: NotBlank
        @get: Size(max = 254)
        var name: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var storingPlace: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var processingMethod: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var remarks: String = "",
        var treasureWrapper: List<TreasuresForm>? = null
)