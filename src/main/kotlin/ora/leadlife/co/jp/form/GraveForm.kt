package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import java.util.*

data class GraveForm(
        var graveId : Long = 0,
        @get: NotBlank
        var isPrepared: Boolean = false,
        @get: NotBlank
        var personWantInheritance: String? = "",
        @get: NotBlank
        var name: String? = "",
        @get: NotBlank
        var postCode1: String? = "",
        @get: NotBlank
        var postCode2: String? = "",
        @get: NotBlank
        var address: String? = "",
        @get: NotBlank
        var contactAddress: String? = "",
        @get: NotBlank
        var graveUser: String? = "",
        @get: NotBlank
        var purchaseName: String? = "",
        @get: NotBlank
        var contractDay: String? = "",
        @get: NotBlank
        var contractMonth: String? = "",
        @get: NotBlank
        var contractYear: String? = "",
        @get: NotBlank
        var contractDate: Date? = null,
        @get: NotBlank
        var religion: String? = "",
        @get: NotBlank
        var management: String? = "",
        @get: NotBlank
        var remarks: String? = "",
        @get: NotBlank
        var cinerationMethod: String? = ""
        ) {

        fun getIsPrepared() : Boolean = this.isPrepared
        fun setIsPrepared(isPrepared : Boolean) {
                this.isPrepared = isPrepared
        }

}