package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "forum_comments")
data class ForumComment(
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

        @Column(nullable = false, name = "account_id", insertable = false, updatable = false)
        var accountId: Long = 0,

        @Column(nullable = false, name = "forum_id", insertable = false, updatable = false)
        var forumId: Long = 0,

        @Column(columnDefinition = "TEXT")
        var contents: String? = null,

        @Column(nullable = false)
        var name: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account(),

        @ManyToOne
        @JoinColumn(name = "forum_id", nullable = false)
        @JsonBackReference
        var forum: Forum = Forum()
)
