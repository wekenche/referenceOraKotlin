package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import java.util.*

data class PaymentForm(
        var paymentId : Long = 0,
        @get: NotBlank
        var paydate: Date = Date(),
        @get: NotBlank
        var amount: Int = 0,
        var appleReceipt: String? = null,
        var accountId:Long? = null,
        var appleTransactionId: Long? = null,
        var appleProductId: String? = null
        ) {
}