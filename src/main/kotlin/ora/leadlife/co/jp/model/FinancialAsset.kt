package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "financial_assets")
data class FinancialAsset(
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

        @Column(name = "name" , length = 254, columnDefinition = "名称・銘柄・内容")
        var name: String = "",

        @Column(name = "holder" , length = 254, columnDefinition = "名義人")
        var holder: String = "",

        @Column(name = "company" , length = 254, columnDefinition = "証券会社・金融機関・取扱会社")
        var company: String = "",

        @Column(name = "contact_address" , length = 254, columnDefinition = "連絡先")
        var contactAddress: String = "",

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String = "",

        @Column(name = "symbol_no" , length = 254, columnDefinition = "記号番号など")
        var symbolNo: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
) {
    override fun hashCode(): Int {
        return id!!.hashCode()
    }
}