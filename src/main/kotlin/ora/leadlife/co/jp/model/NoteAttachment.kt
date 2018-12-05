package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "note_attachments")
data class NoteAttachment(
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
        @Column(name = "name")
        var name: String = "",
        @Column(name = "file_type")
        var fileType: String = "",
        @Column(name = "file_path")
        var filePath: String = "",
        @Column(name = "memo", columnDefinition = "TEXT")
        var memo: String = "",

        @ManyToOne
        @JoinColumn(name = "note_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var note: Note = Note()
)
