package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="credit_cards")
data class CreditCard(
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

        @Column(name = "brand" , length = 254, columnDefinition = "クレジットカードのブランド")
        var brand: String = "",

        @Column(name = "card_no" , length = 254)
        var cardNo: String = "",

        @Column(name = "contact_address" , length = 254, columnDefinition = "紛失時の連絡先")
        var contactAddress: String = "",

        @Column(name = "web_id" , length = 254)
        var webId: String = "",

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

        ) {
}