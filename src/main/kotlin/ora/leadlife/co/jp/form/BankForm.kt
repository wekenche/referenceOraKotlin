package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class BankForm (
        @get: NotBlank
        @get: Size(max = 254)
        var name: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var branchName: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var depositType: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var bankAccountNumber: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var holder: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var webId: String? = "",
        @get: NotBlank
        @get: Size(max = 254)
        var bankUsage: String? = "",

        var bankFormWrapper : List<BankForm>? = null,
        var automaticWithdrawalsWrapper : List<AutomaticWithdrawalsForm>? = null
)