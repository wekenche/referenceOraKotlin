package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "phoneCompanyPasswords")
data class PhoneCompanyPasswords (
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
    @Column(nullable = false, length = 254)
    var password: String = "",

    @Column(nullable = false, name = "account_id", insertable = false, updatable = false)
    var accountId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    var account: Account = Account()
)