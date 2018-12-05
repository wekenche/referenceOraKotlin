package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="lend_moneys")
data class LendMoney(
        @Id
        @GeneratedValue
        var id:Long? = 0,
        @Column(nullable = false, name = "account_id", insertable = false, updatable = false)
        var accountId: Long = 0,
        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),

        @Column(name = "name" , length = 254, columnDefinition = "貸した相手")
        var name: String = "",

        @Column(name = "tel" , length = 254)
        var tel: String = "",

        @Column(name = "post_code1" , length = 10)
        var postCode1: String = "",

        @Column(name = "post_code2" , length = 10)
        var postCode2: String = "",

        @Column(name = "address" , length = 254, columnDefinition = "貸した相手の住所")
        var address: String = "",

        @Column(name = "lend_date")
        var lendDate: Date = Date(),

        @Column(name = "amount" , length = 254, columnDefinition = "金額")
        var amount: String = "",

        @Column(name = "exists_deed")
        var existsDeed: Boolean = false,

        @Column(name = "storing_place" , length = 254, columnDefinition = "保管場所")
        var storingPlace: String = "",

        @Column(name = "balance" , length = 254, columnDefinition = "残高")
        var balance: String = "",

        @Column(name = "memmo" , columnDefinition = "TEXT")
        var memo: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
) {
    override fun hashCode(): Int {
        return id!!.hashCode()
    }
}