package ora.leadlife.co.jp.model


import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "users")
data class User(
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
        @Column(name = "push_id")
        var pushId: String? = null,
        @Column
        var os: String? = null,
        @Column
        var name: String? = null,
        @Column
        var email: String = "",
        @Column(name = "encrypted_password")
        var encryptedPassword: String = "",
        @Column(name = "reset_password_token")
        var resetPasswordToken: String? = null,
        @Column(name = "passed_date")
        var passedDate: Date? = null,
        @Column(name = "confirming_date")
        var confirmingDate: Date? = null,
        @Column(name = "user_type")
        var userType: String = "",
        @Column(name = "authentication_method")
        var authenticationMethod: String = "",
        @Column(name = "is_valid")
        var isValid: Boolean = false,
        @Column(name = "shop_id",  insertable = false, nullable = true, updatable = false)
        var shop_id: Long? = 0,

        @ManyToOne
        @JoinColumn(name = "shop_id", nullable = true)
        @JsonBackReference
        var shop: Shop? = null,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "last_used_account_id", nullable = true)
        @JsonIgnore
        var lastUsedAccount: Account? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "user")
        var accountList: MutableList<Account>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "user")
        var bereaveList: List<Bereave>? = null
) : UserDetails {
    override fun getPassword(): String {
        return this.encryptedPassword
    }

    override fun getUsername(): String {
        return email
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return AuthorityUtils.createAuthorityList(this.userType)
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

