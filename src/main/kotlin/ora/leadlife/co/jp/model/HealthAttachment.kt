package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="health_files")
data class HealthAttachment(
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
        @Column(name = "file_path")
        var filePath: String? = "",
        @Column(name = "file_type")
        var fileType: String? = "",
        @Column(name = "file_name")
        var fileName: String? = "",
        @Column(name = "file_size")
        var fileSize: Int? = 0,

        @ManyToOne
        @JoinColumn(name="account_id",nullable = false)
        @JsonBackReference
        var account : Account = Account()

)