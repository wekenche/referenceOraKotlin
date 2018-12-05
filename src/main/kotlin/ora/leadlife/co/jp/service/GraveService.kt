package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.GraveForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Graves
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.repository.GravesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import javax.transaction.Transactional

@Service
class GraveService {

    @Autowired
    lateinit var gravesRepository: GravesRepository

    fun findByAccountId(account: Account) : Graves? {
        return gravesRepository.findByAccountId(account.id!!.toLong())
    }
    /**
     * 保存
     */
    @Transactional
    fun save(g: GraveForm, user: User): Graves {
        var account = user.lastUsedAccount!!

        var newDate: Date? = null
        if(g.contractYear!="" && g.contractMonth!="" && g.contractDay!=""){
            var date = g.contractYear + "-" + g.contractMonth + "-" + g.contractDay
            newDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
        }
        return gravesRepository.save(Graves(
                id = g.graveId,
                account = account,
                isPrepared = g.isPrepared,
                personWantInheritance = g.personWantInheritance,
                name = g.name,
                postCode1 = g.postCode1,
                postCode2 = g.postCode2 ,
                address = g.address ,
                contactAddress = g.contactAddress ,
                graveUser = g.graveUser,
                purchaseStoreName = g.purchaseName,
                contractDate = newDate,
                religionType = g.religion,
                managementStatus = g.management,
                memo = g.remarks,
                cinerationMethod = g.cinerationMethod
        ))
    }
}