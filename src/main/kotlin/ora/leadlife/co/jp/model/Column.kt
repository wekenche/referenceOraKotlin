package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "columns")
data class Column(
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
        @Column
        var title: String? = null,
        @Column(nullable = false, name = "category_id", insertable = false, updatable = false)
        var categoryId: Long = 0,
        @Column(columnDefinition = "TEXT")
        var contents: String? = null,
        @ManyToOne
        @JoinColumn(name = "account_id", insertable = false, updatable = false)
        @JsonBackReference
        var account: Account,
        @ManyToOne
        @JoinColumn(name = "category_id", insertable = false, updatable = false)
        @JsonBackReference
        var category: Category
)
