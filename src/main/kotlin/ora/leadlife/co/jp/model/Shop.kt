package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

/**
 * 代理店テーブル
 */
@Entity
@Table(name = "shops")
data class Shop(
        @Id
        @GeneratedValue
        var id: Long? = 0,
        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @javax.persistence.Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),
        @Column(name = "name", nullable = false)
        var name: String = "",
        @Column(name = "code", nullable = false)
        var code: String? = "",
        @Column(name = "back_percent", nullable = false)
        var backPercent: Int = 0,
        @Column(name = "back_parent_percent", nullable = false)
        var backParentPercent: Int = 0,
        @Column(name = "parent_id", insertable = false, nullable = true, updatable = false)
        var parentId: Long? = 0,
        @ManyToOne
        @JoinColumn(name = "parent_id", nullable = true)
        @JsonBackReference
        var parent: Shop? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "parent")
        var children: List<Shop>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "shop")
        var users: List<User>? = null
) {
        override fun hashCode(): Int {
                return id!!.hashCode()
        }
}
