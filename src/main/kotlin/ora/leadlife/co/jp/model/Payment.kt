package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "payments")
data class Payment(
        @Id
        @GeneratedValue
        var id: Long? = 0,
        @javax.persistence.Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),
        @Column(nullable = false, name = "account_id")
        var accountId: Long = 0,
        @Column(nullable = false, name = "paydate")
        var paydate: Date = Date(),
        @Column(nullable = false, name = "amount")
        var amount: Int = 0,
        @Column(nullable = false, name = "order_id")
        var orderId: String? = null,
        @Column(nullable = false, name = "mstatus")
        var mstatus: String? = null,
        @Column(nullable = false, name = "v_result_code")
        var vResultCode: String? = null,
        @Column(nullable = true, name = "apple_receipt", columnDefinition = "LONGTEXT")
        var appleReceipt: String? = null,
        @Column(nullable = true, name = "apple_transaction_id")
        var appleTransactionId: Long? = null,
        @Column(nullable = true, name = "apple_product_id")
        var appleProductId: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = true, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "payment")
        var shopPayHistoryPayment: List<ShopPayHistoryPayment>? = null
)