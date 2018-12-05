package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile
import java.sql.Blob
import java.util.*
import javax.validation.constraints.NotNull

/**
 * 死亡確認フォーム
 */
data class DiedConfirmationForm(
        @get: NotBlank
        var id: String = "",
        @get:NotNull
        var date: Date? = null,
        var file1: MultipartFile? = null,
        var file2: MultipartFile? = null
)