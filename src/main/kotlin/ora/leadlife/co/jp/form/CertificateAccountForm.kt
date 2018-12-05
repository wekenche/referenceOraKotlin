package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class CertificateAccountForm(
        var caId : Long = 0,
        @get: NotBlank
        var caName: String = "",
        @get: NotBlank
        var bankAccountNumber: String = "",
        @get: NotBlank
        var holder: String = "",
        @get: NotBlank
        var webId: String = "",
        @get: NotBlank
        var contactAddress: String = "",
        @get: NotBlank
        var remarks: String = "",
        var caWrapper : List<CertificateAccountForm>? = null
) {
}