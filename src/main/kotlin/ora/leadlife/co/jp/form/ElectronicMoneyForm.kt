package ora.leadlife.co.jp.form
import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class ElectronicMoneyForm (
            @get: NotBlank
            @get:Size(max = 254)
            var name: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var no: String = "",
            @get: NotBlank
            @get:Size(max = 254)
            var certificateCompany: String = "",
            var electronicMoneyWrapper: List<ElectronicMoneyForm>? = null

)