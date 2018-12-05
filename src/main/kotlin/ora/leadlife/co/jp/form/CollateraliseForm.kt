package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class CollateraliseForm (
        var collateralId: Long? = 0,
        @get: NotBlank
        var name: String = ""
)