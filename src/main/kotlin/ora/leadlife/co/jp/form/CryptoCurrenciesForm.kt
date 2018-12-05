package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class CryptoCurrenciesForm(
        @get: NotBlank
        @get: Size(max = 254)
        var market: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var cryptoCurrencyType: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var amount: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var cryptoCurrencyId: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var password: String = "",
        @get: NotBlank
        @get: Size(max = 254)
        var remarks: String = "",

        var cryptoCurrenciesWrapper : List<CryptoCurrenciesForm>? = null
)
