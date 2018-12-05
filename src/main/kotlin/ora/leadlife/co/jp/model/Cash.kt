package ora.leadlife.co.jp.model


import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column
import javax.xml.soap.Text

@Entity
@Table(name = "cashes")
data class Cash(

        @Id
        @GeneratedValue
        var id: Long? = 0,
        @Column(nullable = false, name = "account_id", insertable = false, updatable = false)
        var accountId: Long = 0,
        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),

        var amount: Int = 0,
        @Column(name = " storage_location", columnDefinition = "TEXT")
        var storageLocation: String = "",
        @Column(name = "message", columnDefinition = "TEXT")
        var message: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()






)


