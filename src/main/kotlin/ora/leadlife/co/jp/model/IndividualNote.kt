package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "individual_notes")
data class IndividualNote(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0,
        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),
        @Column(name = "title")
        var title: String? = null,
        @Column(name = "type")
        var type: String? = null,
        @Column(name = "contents", columnDefinition = "TEXT")
        var contents: String? = null,
        @Column(nullable = false, name = "individual_note_category_id")
        var individualNoteCategoryId: Long = 0,
        @Column(nullable = false, name = "account_id")
        var accountId: Long? = 0,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account = Account(),
        @ManyToOne
        @JoinColumn(name = "individual_note_category_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var individualNoteCategory: IndividualNoteCategory = IndividualNoteCategory(),

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "individualNote")
        var individualNoteAttachmentList: List<IndividualNoteAttachment>? = null
)
