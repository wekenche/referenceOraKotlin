package ora.leadlife.co.jp.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "categories")
data class Category(
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
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "category")
        var forumList: List<Forum>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "category")
        var columnList: List<ora.leadlife.co.jp.model.Column>? = null
)
