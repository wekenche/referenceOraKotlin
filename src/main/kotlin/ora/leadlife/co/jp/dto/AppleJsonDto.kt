package ora.leadlife.co.jp.dto

data class AppleJsonDto(
        var status: Int = 0,
        var environment: String = "",
        var receipt: AppleJsonReceiptDto? = null,
        var latest_receipt_info: Array<AppleJsonInAppDto>? = null,
        var latest_receipt:String =""
) {
    fun lastLatestReceiptInfo(): AppleJsonInAppDto {
        latest_receipt_info!!.sortBy { s -> s.purchase_date_ms }
        return latest_receipt_info!!.last()
    }
}
