package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class CreditCardForm(
        var creditCardId : Long = 0,
        @get: NotBlank
        var creditCardName: String = "",
        @get: NotBlank
        var creditCardBrand: String = "",
        @get: NotBlank
        var creditCardNumber: String = "",
        @get: NotBlank
        var contactInfo: String = "",
        @get: NotBlank
        var web: String = "",
        @get: NotBlank
        var remarks: String = "",
        var creditCardWrapper : List<CreditCardForm>? = null
) {
}