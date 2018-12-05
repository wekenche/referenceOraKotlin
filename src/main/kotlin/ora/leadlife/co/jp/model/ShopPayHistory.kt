package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "shop_pay_histories")
data class ShopPayHistory(
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
        @Column(name="amount", nullable = false)
        var amount: Int = 0,
        @Column(name="planed_amount", nullable = false)
        var planedAmount: Int = 0,
        @Column(name="payed", nullable = false)
        var payed: Boolean = false,
        @Column(name = "pay_date", nullable = false)
        var payDate: Date = Date(),


        @ManyToOne
        @JoinColumn(name = "shop_id", nullable = false)
        @JsonBackReference
        var shop: Shop = Shop(),

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "shopPayHistory")
        var shopPayHistoryPayment: List<ShopPayHistoryPayment>? = null
)
