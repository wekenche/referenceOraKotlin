package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="collateralise")
class Collateralise(
        @Id
        @GeneratedValue
        var id:Long? = 0,
        @Column(nullable = false, name = "loan_id", insertable = false, updatable = false)
        var loanId: Long = 0,
        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date = Date(),
        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date = Date(),

        @Column(name = "name" , length = 254)
        var name: String = "",

        @ManyToOne
        @JoinColumn(name = "loan_id", nullable = false)
        @JsonBackReference
        var loan: Loan = Loan()
        ) {
}