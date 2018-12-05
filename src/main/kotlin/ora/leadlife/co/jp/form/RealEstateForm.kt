package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class RealEstateForm(
        var estateId : Long = 0,
        @get: NotBlank
        var address : String = "",
        @get: NotBlank
        var parcelNumber : String = "",
        @get: NotBlank
        var equity : String = "",
        @get: NotBlank
        var landCategory : String = "",
        @get: NotBlank
        var memo : String = "",
        var estateWrapper : List<RealEstateForm>? = null
) {
}