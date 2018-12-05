package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "healths")
data class Healths(
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
        @Column(name = "allergy", columnDefinition = "TEXT")
        var allergy: String = "",
        @Column(name = "blood_type", length = 10)
        var bloodType: String? = "",
        @Column (name = "medical_history", columnDefinition = "TEXT")
        var medicalHistory: String? ="",
        @Column (name = "allergic_exists_medicine", columnDefinition = "TEXT")
        var allergicExistsMedicine: String? ="",
        @Column (name = "regular_hospital", columnDefinition = "TEXT")
        var regularHospital: String? ="",
        @Column (name =" want_significant_sick_announcement")
        var wantSignificantSickAnnouncement: Boolean = false,
        @Column (name =" want_resuscitate")
        var wantResuscitate: Boolean = false,
        @Column (name =" want_organ_donation")
        var wantOrganDonation: Boolean = false,
        @Column (name =" has_donor_card")
        var hasDonorCard: Boolean = false,
        @Column (name =" donor_card_location", length = 254)
        var donorCardLocation: String? = "",

        @OneToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

)