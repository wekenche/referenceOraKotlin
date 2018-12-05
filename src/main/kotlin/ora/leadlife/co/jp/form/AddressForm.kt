package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class AddressForm(
        var addressId : Long = 0,
        @get: NotBlank
        var address : String = "",
        @get: NotBlank
        var postCode2 : String = "",
        @get: NotBlank
        var postCode1 : String = "",
        var addWrapper : List<AddressForm>? = null
) {
}