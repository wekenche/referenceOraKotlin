package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class TestamentSpecialistForm(
        var id : Long = 0,
        @get: NotBlank
        var name : String = "",
        @get: NotBlank
        var name2 : String = "",
        @get: NotBlank
        var postCode1 : String = "",
        @get: NotBlank
        var postCode2 : String = "",
        @get: NotBlank
        var streetAddress : String = "",
        @get: NotBlank
        var number : String = ""
) {
}