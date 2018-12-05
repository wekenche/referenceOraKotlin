package ora.leadlife.co.jp.form

import com.sun.org.apache.xpath.internal.operations.Bool
import org.hibernate.validator.constraints.NotBlank

data class StockForm(
        var stockId : Long = 0,
        @get: NotBlank
        var company: String = "",
        @get: NotBlank
        var brand: String = "",
        @get: NotBlank
        var number: String = "",
        @get: NotBlank
        var quantity: String = "",
        @get: NotBlank
        var remarks: String = "",
        @get: NotBlank
        var isCustomManagement: Boolean = false,
        var stockWrapper : List<StockForm>? = null
) {
        fun getIsCustomManagement() : Boolean {
                return this.isCustomManagement!!
        }

        fun setIsCustomManagement(isCustomManagement: Boolean) {
                this.isCustomManagement = isCustomManagement
        }
}