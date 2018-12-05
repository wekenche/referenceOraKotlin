package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "graves")
data class Graves(
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

        @Column(name = "is_prepared")
        var isPrepared: Boolean? = false,

        @Column(name = "person_want_inheritance", length = 254, columnDefinition = "墓を継承して欲しい人")
        var personWantInheritance: String? = null,

        @Column(length = 254)
        var name: String? = null,

        @Column(name = "post_code1", length = 10)
        var postCode1: String? = null,

        @Column(name = "post_code2", length = 10)
        var postCode2: String? = null,

        @Column(length = 254)
        var address: String? = null,

        @Column(name = "contact_address", length = 254)
        var contactAddress: String? = null,

        @Column(name = "grave_user", length = 254, columnDefinition = "墓地使用者")
        var graveUser: String? = null,

        @Column(name = "purchase_store_name", length = 254, columnDefinition = "購入店名")
        var purchaseStoreName: String? = null,

        @Column(name = "contract_date")
        @Temporal(TemporalType.TIMESTAMP)
        var contractDate: Date? = null,

        @Column(name = "religion_type", length = 254, columnDefinition = "宗派")
        var religionType: String? = null,

        @Column(name = "management_status", length = 254, columnDefinition = "管理状況")
        var managementStatus: String? = null,

        @Column(name="memo", columnDefinition = "TEXT")
        var memo: String? = null,

        @Column(name = "cineration_method", length = 254, columnDefinition = "納骨方法の要望")
        var cinerationMethod: String? = null,

        @OneToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()
)