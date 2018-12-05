package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "individual_note_bereaves")
data class IndividualNoteBereave(
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
        @Column
        var status: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account = Account(),
        @ManyToOne
        @JoinColumn(name = "individual_note_category_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var individualNoteCategory: IndividualNoteCategory = IndividualNoteCategory()
)
