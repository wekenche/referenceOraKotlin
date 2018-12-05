package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class SettingForm(
        var id : Long = 0,
        @get: NotBlank
        var email : String? = "",
        @get: NotBlank
        var delegate_tel_no : String? = "",
        @get: NotBlank
        var organizer_email : String? = "",
        @get: NotBlank
        var lawyer_tel_no : String? = "",
        @get: NotBlank
        var lawyer_email : String? = "",
        @get: NotBlank
        var normalFee : Int = 0,
        @get: NotBlank
        var discountedFee : Int = 0,
        @get: NotBlank
        var tax : Int = 0,
        @get: NotBlank
        var veritransGroupId : String? = ""

) {
}