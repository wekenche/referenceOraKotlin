package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="assurance_debts")
data class AssuranceDebt(
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
//        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "assurance_date")
        var assuranceDate: Date? = null,

        @Column(name = "assurance_price" , length = 254, columnDefinition = "保証した金額")
        var assurancePrice: String = "",

        @Column(name = "assurance_target" , length = 254, columnDefinition = "主債務者")
        var assuranceTarget: String = "",

        @Column(name = "assurance_target_contact_address" , length = 254, columnDefinition = "主債務者の連絡先'")
        var assuranceTargetContactAddress:String = "",

        @Column(name = "creditor" , length = 254, columnDefinition = "債権者")
        var creditor: String = "",

        @Column(name = "creditor_contact_address" , length = 254, columnDefinition = "債権者の連絡先")
        var creditorContractAddress: String = "",


        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

) {
    override fun hashCode(): Int {
        return id!!.hashCode()
    }
}