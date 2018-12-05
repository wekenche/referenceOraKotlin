package ora.leadlife.co.jp.dto

import java.util.*

data class AppleJsonInAppDto(
        var expires_date_ms: Long = 0L,
        var transaction_id: Long = 0L,
        var product_id: String = "",
        var purchase_date_ms: Long = 0
) {
    fun getPurchaseDate(): Date {
        return Date(purchase_date_ms)
    }
}
