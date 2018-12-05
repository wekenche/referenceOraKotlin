package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "notes")
data class Note(
        @Id
        @GeneratedValue
        var id: Long? = 0,
        @Column(name = "created_at")
        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var updatedAt: Date = Date(),
        @Column(nullable = false, name = "account_id", insertable = false, updatable = false)
        var accountId: Long = 0,
        @Column
        var title: String = "",
        @Column(columnDefinition = "TEXT")
        var contents: String = "",
        @Column(nullable = false, name = "note_category_id", insertable = false, updatable = false)
        var noteCategoryId: Long = 0,
        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account = Account(),
        @ManyToOne
        @JoinColumn(name = "note_category_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var noteCategory: NoteCategory = NoteCategory()
)
