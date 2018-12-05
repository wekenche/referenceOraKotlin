package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "individual_note_categories")
data class IndividualNoteCategory(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = 0,
        @javax.persistence.Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),
        @Column(name = "name", nullable = false)
        var name: String = "",
        @Column(nullable = true, name = "individual_note_category_template_id")
        var individualNoteCategoryTemplateId: Long? = null,
        @Column(nullable = true, name = "account_id")
        var accountId: Long? = 0,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account = Account(),

        @ManyToOne
        @JoinColumn(name = "individual_note_category_template_id", nullable = true, insertable = false, updatable = false)
        @JsonBackReference
        var individualNoteCategoryTemplate: IndividualNoteCategoryTemplate? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "individualNoteCategory")
        var individualNoteList: List<IndividualNote>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "individualNoteCategory")
        var individualNoteBereaveList: List<IndividualNoteBereave>? = null
)
