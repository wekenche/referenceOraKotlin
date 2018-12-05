package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "individual_note_attachments")
data class IndividualNoteAttachment(
        @Id
        @GeneratedValue
        var id: Long? = 0,
        @Column(name = "individual_note_id", nullable = true)
        var individualNoteId:Long? = 0,
        @javax.persistence.Column(name = "created_at")
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
        @Column(name="file_size")
        var fileSize:Long? = 0,

        @ManyToOne
        @JoinColumn(name = "individual_note_id", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        var individualNote: IndividualNote = IndividualNote()
)
