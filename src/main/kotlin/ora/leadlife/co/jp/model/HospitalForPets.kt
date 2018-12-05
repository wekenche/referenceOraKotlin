package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "hospital_for_pets")
data class HospitalForPets(
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

        @Column(name = "disease_name", columnDefinition = "病名", length = 254)
        var diseaseName: String? = null,

        @Column(columnDefinition = "かかりつけの動物病院", length = 254)
        var hospital: String? = null,

        @Column(name = "contact_address", length = 254)
        var contactAddress: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
)