package ora.leadlife.co.jp.dto

data class AppleJsonReceiptDto(
        var receipt_type: String = "",
        var in_app: Array<AppleJsonInAppDto>? = null
){
    fun lastInApp(): AppleJsonInAppDto {
        in_app!!.sortBy{ s -> s.purchase_date_ms}
        return in_app!!.last()
    }
}
