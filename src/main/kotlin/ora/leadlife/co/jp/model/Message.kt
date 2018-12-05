package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "messages")
data class Message(
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
        var title: String = "",
        @Column(name = "comment", columnDefinition = "TEXT")
        var comment: String = "",
        @Column(name = "message_type")
        var messageType: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account(),
        @ManyToOne
        @JoinColumn(name = "message_category_id", nullable = false)
        @JsonBackReference
        var messageCategory: MessageCategory = MessageCategory(),

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "message")
        var messageAttachmentList: List<MessageAttachment>? = null
)
