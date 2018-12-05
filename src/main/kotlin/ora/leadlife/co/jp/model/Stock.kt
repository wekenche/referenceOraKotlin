package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "stocks")
data class Stock(
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

    @Column(name = "certificate_company" , length = 254, columnDefinition = "証券会社")
    var certificateCompany: String = "",

    @Column(name = "brand" , length = 254, columnDefinition = "銘柄")
    var brand: String = "",

    @Column(name = "certificate_no" , length = 254, columnDefinition = "証券番号等")
    var certificateNo: String = "",

    @Column(name = "quantity" , length = 254, columnDefinition = "数量")
    var quantity: String = "",

    @Column(name = "is_custody_management")
    var isCustomManagement: Boolean = false,

    @Column(name = "memo", columnDefinition = "TEXT")
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