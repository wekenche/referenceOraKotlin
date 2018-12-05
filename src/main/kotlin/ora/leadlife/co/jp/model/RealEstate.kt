package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "realEstate")
data class RealEstate(
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
        @Column(length = 254)
        var address: String? = "",
        @Column(name ="parcel_number", length = 254)
        var parcelNumber: String? = "",
        @Column(name ="land_category", length = 254)
        var landCategory: String? = "",
        @Column(length = 254)
        var equity: String? = "",
        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String? = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()




        )