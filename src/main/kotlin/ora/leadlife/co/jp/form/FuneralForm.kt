package ora.leadlife.co.jp.form

import javafx.beans.property.ReadOnlyListWrapper
import org.hibernate.validator.constraints.NotBlank

data class FuneralForm(
        var funeralId : Long = 0,
        @get: NotBlank
        var funeralName: String = "",
        @get: NotBlank
        var isReservation: Boolean = true,
        @get: NotBlank
        var funeralFee: String = "",
        @get: NotBlank
        var funeralCompany: String = "",
        @get: NotBlank
        var funeralContactAddress: String = "",
        @get: NotBlank
        var funeralReligionType: String = "",
        @get: NotBlank
        var memo: String = "",

        var funeralListWrapper: List<FuneralListForm>? = null,
        var funeralWrapper: List<FuneralForm>? = null
) {
        fun getIsReservation() : Boolean {
                return this.isReservation!!
        }

        fun setIsReservation(isReservation: Boolean) {
                this.isReservation = isReservation
        }
}