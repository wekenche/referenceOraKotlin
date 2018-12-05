package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

/**
 * 退会フォーム
 */
data class WithdrawalForm(
        @get: NotBlank
        var password: String = "",
        @get: NotBlank
        var passwordConfirm: String = ""
)