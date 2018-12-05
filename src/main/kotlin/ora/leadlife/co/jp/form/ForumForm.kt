package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * 投稿フォーム
 */
data class ForumForm(
        @get: NotBlank
        @get: Size(max = 254)
        var name: String = "",

        @get: NotBlank
        @get: Size(max = 254)
        var title: String = "",

        @get: NotBlank
        var contents: String = ""
)