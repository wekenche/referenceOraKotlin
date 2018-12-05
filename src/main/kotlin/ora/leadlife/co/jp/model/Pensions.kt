package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "pensions")
data class Pensions(
        @Id
        @GeneratedValue
        var id: Long? = 0,

        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),

        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),

        @Column(name = "pension_no", length = 254, columnDefinition = "公的年金の基礎年金番号")
        var pensionNo: String? = null,

        @Column(name = "pension_type", length = 254, columnDefinition = "加入したことのある年金の種類")
        var pensionType: String? = null,

        @Column(name = "private_pension", length = 254, columnDefinition = "私的年金の名称")
        var privatePension: String? = null,

        @Column(name = "contact_address", length = 254)
        var contactAddress: String? = null,

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
)