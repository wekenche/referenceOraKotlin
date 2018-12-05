package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class DigitalForm(
        var devicePasswordFormWrapper: List<DevicePasswordForm>? = null,
        var emailFormWrapper: List<EmailForm>? = null,
        var websFormWrapper: List<WebsForm>? = null,
        @get: NotBlank
        @get: Size(max = 254)
        var password: String = "",
        var id: Long? = 0
)