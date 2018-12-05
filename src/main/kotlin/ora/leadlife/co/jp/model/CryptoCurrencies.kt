package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "crypto_currencies")
data class CryptoCurrencies(
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

        @Column(length = 254, columnDefinition = "取引所・ウォレット")
        var market: String? = null,

        @Column(name = "crypto_currency_type", length = 254, columnDefinition = "保有仮想通貨の種類")
        var cryptoCurrencyType: String? = null,

        @Column(length = 254, columnDefinition = "仮想通貨の額")
        var amount: String? = null,

        @Column(name = "crypto_currency_id", length = 254)
        var cryptoCurrencyId: String? = null,

        @Column(length = 254, columnDefinition = "暗号化されたパスワード")
        var password: String? = null,

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
)