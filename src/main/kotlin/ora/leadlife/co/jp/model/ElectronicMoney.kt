package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column


@Entity
@Table(name="electronic_moneys")
data class ElectronicMoney (
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

    @Column(name = "name" , length = 254)
    var name: String = "",

    @Column(name = "no" , length = 254)
    var no: String = "",

    @Column(name = "contact_address" , length = 254)
    var certificateCompany: String = "",

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    var account: Account = Account()
    )
{
    override fun hashCode(): Int {
        return id!!.hashCode()
    }
}