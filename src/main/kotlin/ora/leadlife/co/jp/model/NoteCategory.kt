package ora.leadlife.co.jp.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "note_categories")
data class NoteCategory(
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
        var name: String = "",
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "noteCategory")
        var noteList: List<Note>? = null
)
