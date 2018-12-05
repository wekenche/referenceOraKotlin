package ora.leadlife.co.jp.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.persistence.Column



@Entity
@Table(name = "accounts")
data class Account(
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
        @Column(nullable = false, name = "user_id", insertable = false, updatable = false)
        var userId: Long = 0,
        var name: String = "",

        @Column(name = "is_premium")
        var isPremium: Boolean = false,

        @Column(name = "premium_pay_last_date")
        var premiumPayLastDate: Date? = null,

        @Column(name = "died_confirmed")
        var died_confirmed: Boolean = false,

        @Column(name = "died_confirmation_file_path1")
        var path1: String = "",
        @Column(name = "died_confirmation_file_path2")
        var path2: String = "",
        @Column(name = "died_confirmation_file_size1")
        var size1: Long? = 0,
        @Column(name = "died_confirmation_file_size2")
        var size2: Long? = 0,

        @Column(name = "died_confirmed_admin")
        var diedConfirmedAdmin: Boolean = false,

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        @JsonBackReference
        var user: User = User(),
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var forumList: List<Forum>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var columnList: List<ora.leadlife.co.jp.model.Column>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var bereaveList: List<Bereave>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var noteList: List<Note>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var forumCommentList: List<ForumComment>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var individualNoteCategoryList: List<IndividualNoteCategory>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var individualNoteBereaveList: List<IndividualNoteBereave>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var individualNoteList: List<IndividualNote>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var messageList: List<Message>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var devicePasswords: List<DevicePasswords>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var emails: List<Emails>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var webs: List<Webs>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var medicines: List<Medicines>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var addresses: List<Addresses>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var realEstate: List<RealEstate>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var banks: List<Banks>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var certificateAccounts: List<CertificateAccounts>? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var phoneCompanyPasswords: List<PhoneCompanyPasswords>? = null,
        @OneToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var healths: Healths? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var stockList: List<Stock>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var financialAssetList: List<FinancialAsset>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var depositList: List<Deposit>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var storeList: List<Store>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var lendMoneyList: List<LendMoney>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var loanList: List<Loan>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var assuranceDebtList: List<AssuranceDebt>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var creditCardList: List<CreditCard>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var electronicList: List<ElectronicMoney>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var pensionsList: List<Pensions>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var cryptoCurrencies: List<CryptoCurrencies>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var treasures: List<Treasures>? = null,
        @OneToOne(cascade = arrayOf(CascadeType.REMOVE), mappedBy = "account")
        var funerals: Funerals? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var funeralsList: List<FuneralLists>? = null,
        @OneToOne(cascade = arrayOf(CascadeType.REMOVE), mappedBy = "account")
        var graves: Graves? = null,
        @OneToOne(cascade = arrayOf(CascadeType.REMOVE), mappedBy = "account")
        var testaments: Testaments? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var specialistForTestaments: List<SpecialistForTestaments>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var pets: List<Pets>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var hospitalForPets: List<HospitalForPets>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var insuranceForPets: List<InsuranceForPets>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var salonForPets: List<SalonForPets>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var payments: List<Payment>? = null,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var cashes: List<Cash>? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "account")
        var healthAttachment: List<HealthAttachment>? = null

) {
        override fun hashCode(): Int {
                return id!!.hashCode()
        }

        fun getBereaveDiedCount(): Int {
            return this.bereaveList!!.count{
                it.diedDate != null
            }
        }

        fun getIsPremium(): Boolean {
            return this.isPremium
        }
}
