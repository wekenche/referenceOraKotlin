package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "funerals")
data class Funerals (
        @Id
        @GeneratedValue
        var id : Long?=0,

        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt : Date = Date(),

        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt : Date = Date(),

        @Column(name="is_reservation")
        var isReservation : Boolean? = null,

        @Column(name="funeral_fee", length = 254,columnDefinition = "葬儀代")
        var funeralFee : String? = null,

        @Column(length = 254,columnDefinition = "予約してある葬儀会社の名称")
        var company : String? = null,

        @Column(name = "contact_address",length = 254)
        var contactAddress : String? = null,

        @Column(name = "religion_type",length = 254,columnDefinition = "宗派")
        var religionType : String? = null,

        @Column(name="memo", columnDefinition = "TEXT")
        var memo : String? = null,

        @OneToOne
        @JoinColumn(name="account_id",nullable = false)
        @JsonBackReference
        var account : Account = Account()
)