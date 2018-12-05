package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "forums")
data class Forum(
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

        @Column(nullable = false, name = "account_id")
        var accountId: Long = 0,

        @Column
        var title: String? = null,

        @Column(nullable = true, name = "category_id")
        var categoryId: Long? = null,

        @Column(columnDefinition = "TEXT")
        var contents: String? = null,

        @Column(nullable = false)
        var name: String = "",

        @Column(nullable = false)
        var commentCount: Int = 0,

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = true, insertable = false, updatable = false)
        @JsonBackReference
        var account: Account? = null,

        @ManyToOne
        @JoinColumn(name = "category_id", nullable = true, insertable = false, updatable = false)
        @JsonBackReference
        var category: Category? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "forum")
        @OrderBy("id")
        var forumCommentList: List<ForumComment>? = null
)