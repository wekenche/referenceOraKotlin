package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class FuneralListForm(
        var funeralListId : Long = 0,
        @get: NotBlank
        var funeralListName: String = "",
        @get: NotBlank
        var funeralListPostCode1: String = "",
        @get: NotBlank
        var funeralListPostCode2: String = "",
        @get: NotBlank
        var funeralListAddress: String = "",
        @get: NotBlank
        var funeralListTel: String = "",

        var funeralListWrapper : List<FuneralListForm>? = null
) {
}