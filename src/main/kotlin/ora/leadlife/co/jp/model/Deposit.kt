package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="deposits")
data class Deposit(
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

        @Column(name = "category" , length = 254, columnDefinition = "種類・保険会社")
        var category: String = "",

        @Column(name = "symbol_no" , length = 254, columnDefinition = "保険証書記号番号等")
        var symbolNo: String = "",

        @Column(name = "certificate_no" , length = 254, columnDefinition = "証券番号等")
        var certificateNo: String = "",

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

        )
{

}