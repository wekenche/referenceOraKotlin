package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class LoanForm(
        var loanId : Long? = 0,
        @get: NotBlank
        var target: String = "",
        @get: NotBlank
        var tel: String = "",
        @get: NotBlank
        var postCode1: String = "",
        @get: NotBlank
        var postCode2: String = "",
        @get: NotBlank
        var address: String = "",
        @get: NotBlank
        var dateY: String = "",
        @get: NotBlank
        var dateM: String = "",
        @get: NotBlank
        var dateD: String = "",
        @get: NotBlank
        var amount: String = "",
        @get: NotBlank
        var refundMethod: String = "",
        @get: NotBlank
        var balance: String = "",
        @get: NotBlank
        var purpose: String = "",

        var loanFormWrapper: List<LoanForm>? = null,
        var collateraliseFormWrapper: List<CollateraliseForm>? = null
)