package ora.leadlife.co.jp.form

import ora.leadlife.co.jp.model.ShopPayHistory
import java.util.*

data class ShopForm(
        var shopId : Long = 0,
        var shopName : String = "",
        var shopCode : String = "",
        var shopParent : String? = "",
        var shopParentId : Long? = 0,
        var shopBackPercent : Int = 0,
        var shopBackParentPercent : Int = 0,
        var unpaid:ShopPayHistory? = null,
        var created : Date? = null,
        var updated : Date? = null
)