package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="stores")
data class Store(
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

        @Column(name = "contract_company" , length = 254, columnDefinition = "契約会社")
        var contractCompany: String = "",

        @Column(name = "contact_address" , length = 254, columnDefinition = "連絡先")
        var contractAddress: String = "",

        @Column(name = "post_code1" , length = 10)
        var postCode1: String = "",

        @Column(name = "post_code2" , length = 10)
        var postCode2: String = "",

        @Column(name = "address" , length = 254)
        var address: String = "",

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
) {
    override fun hashCode(): Int {
        return id!!.hashCode()
    }
}