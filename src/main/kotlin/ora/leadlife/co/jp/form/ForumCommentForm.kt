package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * 投稿フォーム
 */
data class ForumCommentForm(
        @get: NotBlank
        @get: Size(max = 254)
        var name: String = "",

        @get: NotBlank
        var contents: String = "",

        @get: NotBlank
        var forumId: String = ""
)