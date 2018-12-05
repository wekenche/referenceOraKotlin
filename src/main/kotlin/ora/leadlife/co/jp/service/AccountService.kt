package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.AccountForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.IndividualNoteCategory
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.AccountRepository
import ora.leadlife.co.jp.repository.IndividualNoteCategoryRepository
import ora.leadlife.co.jp.repository.IndividualNoteCategoryTemplatesRepository
import ora.leadlife.co.jp.repository.PaymentRepository
import ora.leadlife.co.jp.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import kotlin.collections.ArrayList

import ora.leadlife.co.jp.repository.AddressesRepository
import ora.leadlife.co.jp.repository.HealthAttachmentRepository
import ora.leadlife.co.jp.repository.MessageRepository
import ora.leadlife.co.jp.repository.AssuranceDebtRepository
import ora.leadlife.co.jp.repository.BanksRepository
import ora.leadlife.co.jp.repository.CashRepository
import ora.leadlife.co.jp.repository.CertificateAccountsRepository
import ora.leadlife.co.jp.repository.CreditCardRepository
import ora.leadlife.co.jp.repository.CryptoCurrenciesRepository
import ora.leadlife.co.jp.repository.DepositRepository
import ora.leadlife.co.jp.repository.DevicePasswordsRepository
import ora.leadlife.co.jp.repository.ElectronicMoneyRepository
import ora.leadlife.co.jp.repository.EmailsRepository
import ora.leadlife.co.jp.repository.FinancialAssetRepository
import ora.leadlife.co.jp.repository.FuneralsRepository
import ora.leadlife.co.jp.repository.FuneralListsRepository
import ora.leadlife.co.jp.repository.GravesRepository
import ora.leadlife.co.jp.repository.HospitalForPetsRepository
import ora.leadlife.co.jp.repository.InsuranceForPetsRepository
import ora.leadlife.co.jp.repository.LendMoneyRepository
import ora.leadlife.co.jp.repository.LoanRepository
import ora.leadlife.co.jp.repository.PensionsRepository
import ora.leadlife.co.jp.repository.PetsRepository
import ora.leadlife.co.jp.repository.PhoneCompanyPasswordsRepository
import ora.leadlife.co.jp.repository.RealEstateRepository
import ora.leadlife.co.jp.repository.SalonForPetsRepository
import ora.leadlife.co.jp.repository.SpecialistForTestamentsRepository
import ora.leadlife.co.jp.repository.StockRepository
import ora.leadlife.co.jp.repository.StoreRepository
import ora.leadlife.co.jp.repository.TestamentsRepository
import ora.leadlife.co.jp.repository.TreasuresRepository
import ora.leadlife.co.jp.repository.WebsRepository


@Service
class AccountService {
    @Autowired
    lateinit var healthAttachmentRepository: HealthAttachmentRepository

    @Autowired
    lateinit var messageRepository: MessageRepository

    @Autowired
    lateinit var addressesRepository: AddressesRepository

    @Autowired
    lateinit var assuranceDebtRepository: AssuranceDebtRepository

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var banksRepository: BanksRepository

    @Autowired
    lateinit var cashRepository: CashRepository

    @Autowired
    lateinit var certificateAccountsRepository: CertificateAccountsRepository

    @Autowired
    lateinit var creditCardRepository: CreditCardRepository

    @Autowired
    lateinit var cryptoCurrenciesRepository: CryptoCurrenciesRepository

    @Autowired
    lateinit var depositRepository: DepositRepository

    @Autowired
    lateinit var devicePasswordsRepository: DevicePasswordsRepository

    @Autowired
    lateinit var electronicMoneyRepository: ElectronicMoneyRepository

    @Autowired
    lateinit var emailsRepository: EmailsRepository

    @Autowired
    lateinit var financialAssetRepository: FinancialAssetRepository

    @Autowired
    lateinit var funeralListsRepository: FuneralListsRepository

    @Autowired
    lateinit var funeralsRepository: FuneralsRepository

    @Autowired
    lateinit var gravesRepository: GravesRepository

    @Autowired
    lateinit var hospitalForPetsRepository: HospitalForPetsRepository

    @Autowired
    lateinit var insuranceForPetsRepository: InsuranceForPetsRepository

    @Autowired
    lateinit var lendMoneyRepository: LendMoneyRepository

    @Autowired
    lateinit var loanRepository: LoanRepository

    @Autowired
    lateinit var pensionsRepository: PensionsRepository

    @Autowired
    lateinit var petsRepository: PetsRepository

    @Autowired
    lateinit var phoneCompanyPasswordsRepository: PhoneCompanyPasswordsRepository

    @Autowired
    lateinit var realEstateRepository: RealEstateRepository

    @Autowired
    lateinit var salonForPetsRepository: SalonForPetsRepository

    @Autowired
    lateinit var specialistForTestamentsRepository: SpecialistForTestamentsRepository

    @Autowired
    lateinit var stockRepository: StockRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var testamentsRepository: TestamentsRepository

    @Autowired
    lateinit var treasuresRepository: TreasuresRepository

    @Autowired
    lateinit var websRepository: WebsRepository

    @Autowired
    lateinit var individualNoteCategoryTemplatesRepository: IndividualNoteCategoryTemplatesRepository

    @Autowired
    lateinit var individualNoteCategoryRepository: IndividualNoteCategoryRepository

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    fun findByUser(user: User): List<Account> {
        return accountRepository.findByUser(user)
    }

    fun findOneByUser(user: User): Account {
        return accountRepository.findOneByUser(user)
    }

    @Transactional
    fun deleteById(id: String) {
        accountRepository.delete(id.toLong())
    }

    @Transactional
    fun add(accountForm: AccountForm, user: User) {
        val acc = accountRepository.save(Account(user = user, name = accountForm.name))
        individualNoteCategoryTemplatesRepository.findAll().forEach {
            individualNoteCategoryRepository.save(IndividualNoteCategory(name = it.name, individualNoteCategoryTemplate = it, individualNoteCategoryTemplateId = it.id, account = acc, accountId = acc.id))
        }
    }

    fun findById(id: String): Account {
        return accountRepository.findOne(id.toLong())
    }

    fun findByIdToCheckNull(id: String): Account? {
        return accountRepository.findOne(id.toLong())
    }

    fun findById(id: Long): Account {
        return accountRepository.findOne(id)
    }

    fun findTargetDeleteAccounts(startDate: Date, endDate: Date): List<Account> {
        return accountRepository.findAllFreeAccounts(startDate, endDate)
    }

    // 次回更新日を取得
    fun nextPaymentUpdateDate(accountId: Long): Calendar {
        val account = findById(accountId)
        return nextPaymentUpdateDate(account)
    }

    fun nextPaymentUpdateDate(account: Account): Calendar {
        val cal = Calendar.getInstance()
        if (account.premiumPayLastDate == null) {
            val payment = paymentRepository.findFirstByAccountAndMstatusEqualsOrderByIdDesc(account)
            if (payment != null) {
                cal.time = payment.paydate
            }
        } else {
            cal.time = account.premiumPayLastDate
        }
        cal.add(Calendar.DATE, 1)
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE))
        return cal
    }

    /**
     * 決済対象を取得
     */
    fun findTargetRePayAccounts(lastDateCal: Calendar): List<Account> {
        val accounts: ArrayList<Account> = ArrayList()
        for (account in accountRepository.findAccountsNotIOS()) {
            if (DateUtil.compareToDateOnly(lastDateCal, nextPaymentUpdateDate(account)) == 0) {
                accounts.add(account)
            }
        }
        return accounts
    }

    @Transactional
    fun makeFree(account: Account): Account {
        if (account.isPremium) {
            account.isPremium = false
            deleteAll(account)
            save(account)
        }

        return account
    }


    @Transactional
    fun deleteAll(account: Account){
        funeralsRepository.deleteByAccount(account)
        gravesRepository.deleteByAccount(account)
        phoneCompanyPasswordsRepository.deleteByAccount(account)
        testamentsRepository.deleteByAccount(account)
        healthAttachmentRepository.deleteByAccount(account)
        addressesRepository.deleteByAccount(account)
        assuranceDebtRepository.deleteByAccount(account)
        banksRepository.deleteByAccount(account)
        cashRepository.deleteByAccount(account)
        certificateAccountsRepository.deleteByAccount(account)
        creditCardRepository.deleteByAccount(account)
        cryptoCurrenciesRepository.deleteByAccount(account)
        depositRepository.deleteByAccount(account)
        devicePasswordsRepository.deleteByAccount(account)
        electronicMoneyRepository.deleteByAccount(account)
        emailsRepository.deleteByAccount(account)
        financialAssetRepository.deleteByAccount(account)
        funeralListsRepository.deleteByAccount(account)
        hospitalForPetsRepository.deleteByAccount(account)
        insuranceForPetsRepository.deleteByAccount(account)
        lendMoneyRepository.deleteByAccount(account)
        loanRepository.deleteByAccount(account)
        messageRepository.deleteByAccount(account)
        pensionsRepository.deleteByAccount(account)
        petsRepository.deleteByAccount(account)
        realEstateRepository.deleteByAccount(account)
        salonForPetsRepository.deleteByAccount(account)
        specialistForTestamentsRepository.deleteByAccount(account)
        stockRepository.deleteByAccount(account)
        storeRepository.deleteByAccount(account)
        treasuresRepository.deleteByAccount(account)
        websRepository.deleteByAccount(account)
    }

    fun save(account: Account): Account {
        return accountRepository.save(account)
    }
}