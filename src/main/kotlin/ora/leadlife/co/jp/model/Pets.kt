package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "pets")
data class Pets(
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

        @Column(name = "gender")
        var gender: Boolean = false,

        @Column(columnDefinition = "エサ", length = 254)
        var feed: String? = null,

        @Column(name = "exists_pedigree_form")
        var existsPedigreeForm: Boolean = false,

        @Column(name = "pedigree_form_no", length = 254)
        var pedigreeFormNo: String? = null,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()

)