package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class AutomaticWithdrawalsForm(
        @get: NotBlank
        @get: Size(max = 254)
        var expenseItem: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var withdrawalDate: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var memo: String? = ""
)