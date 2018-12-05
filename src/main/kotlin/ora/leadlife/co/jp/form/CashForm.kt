package ora.leadlife.co.jp.form
import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class CashForm (
        @get: NotBlank
        var amount: Int? = 0,
        @get: NotBlank
        var storageLocation: String? = "",
        @get: NotBlank
        var message: String? = "",
        var cashFormWrapper: List<CashForm>? = null

)