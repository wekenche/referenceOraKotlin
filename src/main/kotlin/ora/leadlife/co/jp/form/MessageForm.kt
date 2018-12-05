package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.AssertTrue

/**
 * 退会フォーム
 */
data class MessageForm(
        var messageId: Long = 0,
        @get: NotBlank
        var messageCategory: Long = 0,
        @get: NotBlank
        var messageType: String = "",
        @get: NotBlank
        var messageTitle: String = "",
        @get: NotBlank
        var messageComment: String = "",

        var messageAttachment: String? = null
)