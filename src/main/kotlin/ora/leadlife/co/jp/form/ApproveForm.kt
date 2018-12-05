package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

/**
 * 共有承認用フォーム
 */
data class ApproveForm(
        @get : NotBlank
        var id: String = ""
)