package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "medicines")
data class Medicines(
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
        var name: String? = "",
        @Column(name = "prescription_date")
        var prescriptionDate: Date? = null,
        @Column(length = 254)
        var hospital: String? = "",
        @Column(length = 254)
        var doctor: String? = "",
        @Column(name = "medicine_amount", length = 254)
        var medicineAmount: String? = "",
        @Column(name = "drink_how_to", columnDefinition = "TEXT")
        var drinkHowTo: String? = "",
        @Column(name = "prescription_days", length = 254)
        var prescriptionDays: String? = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

)