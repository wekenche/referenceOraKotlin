package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "automaticWithdrawals")
data class AutomaticWithdrawals(
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
        @Column(name = "expense_item")
        var expenseItem: String? ="",
        @Column(name = "withdrawal_date")
        var withdrawalDate: String? ="",
        @Column(name = "memo",columnDefinition = "TEXT")
        var memo: String? = "",


        @ManyToOne
        @JoinColumn(name = "bank_id", nullable = false)
        @JsonBackReference
        var bank: Banks = Banks()
        )