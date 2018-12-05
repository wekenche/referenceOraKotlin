package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class StoreForm(
        var storeId : Long = 0,
        @get: NotBlank
        var companyName : String = "",
        @get: NotBlank
        var contact : String = "",
        @get: NotBlank
        var postCode1 : String = "",
        @get: NotBlank
        var postCode2 : String = "",
        @get: NotBlank
        var address : String = "",
        @get: NotBlank
        var memo : String = "",
        var storeWrapper : List<StoreForm>? = null
) {
}