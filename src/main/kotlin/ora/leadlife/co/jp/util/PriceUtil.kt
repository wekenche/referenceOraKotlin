package ora.leadlife.co.jp.util

import org.springframework.stereotype.Service

@Service
class PriceUtil {
    companion object {
        /**
         * 小数点以下切り捨て
         */
        fun includingTaxFloor(amount: Int, taxRate: Int): Int {
            return Math.floor(amount * (1 + taxRate * 0.01)).toInt()
        }
    }
}