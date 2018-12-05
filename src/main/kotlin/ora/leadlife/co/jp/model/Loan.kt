package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name="loan")
data class Loan(
        @Id
        @GeneratedValue
        var id:Long? = 0,
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

        @Column(name = "loan_target" , length = 254, columnDefinition = "借入先")
        var loanTarget: String = "",

        @Column(name = "tel" , length = 254)
        var tel: String = "",

        @Column(name = "post_code1" , length = 10)
        var postCode1: String = "",

        @Column(name = "post_code2" , length = 10)
        var postCode2: String = "",

        @Column(name = "address" , length = 254)
        var address: String = "",

        @Column(name = "loan_date")
        var loanDate: Date = Date(),

        @Column(name = "loan_amount" , length = 254, columnDefinition = "借入額")
        var loanAmount: String = "",

        @Column(name = "refund_method" , length = 254, columnDefinition = "返済方法")
        var refundMethod: String = "",

        @Column(name = "loan_balance" , length = 254, columnDefinition = "借入残高")
        var loanBalance: String = "",

        @Column(name = "loan_purpose", columnDefinition = "TEXT")
        var loanPurpose: String = "",

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        @JsonBackReference
        var account: Account = Account()


//        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "loans")
//        var colList: List<Collateralise>? = null
        ) {
}