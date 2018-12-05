package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "insurance_for_pets")
data class InsuranceForPets (
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

        @Column(columnDefinition = "保険会社名", length = 254)
        var company: String? = null,

        @Column(length = 254)
        var tel: String? = null,

        @Column(name = "billing_method", columnDefinition = "請求方法", length = 254)
        var billingMethod: String? = null,

        @Column(name="memo", columnDefinition = "TEXT")
        var memo: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
)