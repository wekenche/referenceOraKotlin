package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "certificateAccounts")
data class CertificateAccounts(
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
        var name:String? = "",
        @Column(name = "bank_account_number",length = 254)
        var bankAccountNumber:String? = "",
        @Column(length = 254)
        var holder:String? = "",
        @Column(name = "web_id",length = 254)
        var webId:String? = "",
        @Column(name = "contact_address",length = 254)
        var contactAddress:String? = "",
        @Column(name = "memo", columnDefinition = "TEXT")
        var memo:String? = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

        )