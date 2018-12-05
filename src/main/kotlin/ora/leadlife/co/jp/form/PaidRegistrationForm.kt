package ora.leadlife.co.jp.form

data class PaidRegistrationForm(
        var token: String = "",
        var amount: Int = 0,
        var password: String = "",
        var passwordConfirm: String = ""
)
