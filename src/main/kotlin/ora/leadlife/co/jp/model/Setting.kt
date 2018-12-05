package ora.leadlife.co.jp.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "settings")
data class Setting(
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
        @Column(name = "email")
        var email: String? = "",
        @Column(name = "delegate_tel_no")
        var delegate_tel_no: String? = "",
        @Column(name = "organizer_email")
        var organizer_email: String? = "",
        @Column(name = "lawyer_tel_no")
        var lawyer_tel_no: String?= "",
        @Column(name = "lawyer_email")
        var lawyer_email: String? = "",
        @Column(name = "normal_fee")
        var normalFee: Int = 0,
        @Column(name = "discounted_fee")
        var discountedFee: Int = 0,
        @Column(name = "tax")
        var tax: Int = 0,
        @Column(name = "veritrans_group_id")
        var veritransGroupId: String? = ""
)