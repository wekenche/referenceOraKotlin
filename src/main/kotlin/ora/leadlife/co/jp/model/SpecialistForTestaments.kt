package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "specialist_for_testaments")
data class SpecialistForTestaments(
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
        var name: String? = null,

        @Column(length = 254, columnDefinition = "名称")
        var name2: String? = null,

        @Column(name = "post_code1", length = 10)
        var postCode1: String? = null,

        @Column(name = "post_code2", length = 10)
        var postCode2: String? = null,

        @Column(length = 254)
        var address: String? = null,

        @Column(length = 254)
        var tel: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
)