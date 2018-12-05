package ora.leadlife.co.jp.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "message_categories")
data class MessageCategory(
        @Id
        @GeneratedValue
        var id: Long? = 0,
        @javax.persistence.Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),
        var name: String = "",

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "messageCategory")
        var messageList: List<Message>? = null

)