package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "treasures")
data class Treasures(
        @Id
        @GeneratedValue
        var id: Long? = 0,

        @Column(name = "account_id", nullable = true)
        var accountId: Long? = 0,

        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),

        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),

        @Column(length = 254, columnDefinition = "種類名称")
        var name: String? = null,

        @Column(name = "storing_place", length = 254, columnDefinition = "保管場所")
        var storingPlace: String? = null,

        @Column(name = "processing_method", length = 254, columnDefinition = "自分の死後の処分の方法")
        var processingMethod: String? = null,

        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account = Account()
)

