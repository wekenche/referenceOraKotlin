package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class TestamentForm(
        var testId : Long = 0,
        @get: NotBlank
        var existsTestament : Boolean = false,
        @get: NotBlank
        var testamentType : String = "notarial",
        @get: NotBlank
        var storingPlace : String = "",
        var tsWrapper : List<TestamentSpecialistForm>? = null
) {
}