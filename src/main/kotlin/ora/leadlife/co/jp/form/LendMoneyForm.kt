package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import java.util.*

data class LendMoneyForm(

        var lendMoneyId : Long = 0,
        @get: NotBlank
        var name : String = "",
        @get: NotBlank
        var tel : String = "",
        @get: NotBlank
        var postCode1 : String = "",
        @get: NotBlank
        var postCode2 : String = "",
        @get: NotBlank
        var address : String = "",
        @get: NotBlank
        var lendDay : String = "",
        @get: NotBlank
        var lendMonth : String = "",
        @get: NotBlank
        var lendYear : String = "",
        @get: NotBlank
        var lendDate : Date? = null,
        @get: NotBlank
        var amount : String = "",
        @get: NotBlank
        var existDeed : Boolean = false,
        @get: NotBlank
        var storingPlace : String = "",
        @get: NotBlank
        var balance : String = "",
        @get: NotBlank
        var remarks : String = "",
        var lmWrapper : List<LendMoneyForm>? = null
) {
        fun getIsExistDeed() : Boolean = this.existDeed
        fun setIsExistDeed(isExistDeed : Boolean) {
                this.existDeed = isExistDeed
        }
}