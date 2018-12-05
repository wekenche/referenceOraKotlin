package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "banks")
data class Banks(
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
        @Column(name = "branch_name", length = 254)
        var branchName: String? = "",
        @Column(name = "deposit_type", length = 254)
        var depositType: String? = "",
        @Column(name = "bank_account_number", length = 254)
        var bankAccountNumber: String? = "",
        @Column(length = 254)
        var holder: String? = "",
        @Column(name = "web_id", length = 254)
        var webId: String? = "",
        @Column(name = "bank_usage",columnDefinition = "TEXT")
        var bankUsage: String? = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account(),

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "bank")
        var automaticWithdrawals: List<AutomaticWithdrawals>? = null

)