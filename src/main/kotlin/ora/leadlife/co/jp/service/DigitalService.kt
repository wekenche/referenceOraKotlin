package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.DevicePasswordForm
import ora.leadlife.co.jp.form.DigitalForm
import ora.leadlife.co.jp.form.EmailForm
import ora.leadlife.co.jp.form.WebsForm
import ora.leadlife.co.jp.model.*
import ora.leadlife.co.jp.repository.DevicePasswordsRepository
import ora.leadlife.co.jp.repository.EmailsRepository
import ora.leadlife.co.jp.repository.PhoneCompanyPasswordsRepository
import ora.leadlife.co.jp.repository.WebsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DigitalService {
    @Autowired
    lateinit var devicePasswords: DevicePasswordsRepository
    @Autowired
    lateinit var phoneCompanyPasswordsRepository: PhoneCompanyPasswordsRepository
    @Autowired
    lateinit var emailsRepository: EmailsRepository
    @Autowired
    lateinit var websRepository: WebsRepository

    fun findOneByAccount(account: Account) : PhoneCompanyPasswords? = phoneCompanyPasswordsRepository.findFirstByAccountOrderByIdAsc(account)
    fun findAllByAccount(account: Account):DigitalForm {
        val digitalForm = DigitalForm()
        var deviceList = mutableListOf<DevicePasswordForm>()
        var emailList = mutableListOf<EmailForm>()
        var webList = mutableListOf<WebsForm>()
        devicePasswords.findByAccount(account).forEach{
            deviceList.add(DevicePasswordForm(name = it.name,password = it.password))
        }
        emailsRepository.findByAccount(account).forEach {
            emailList.add(EmailForm(email = it.email,password = it.password))
        }
        websRepository.findByAccount(account).forEach {
            webList.add(WebsForm(name = it.name,webId = it.webId,password = it.password,remarks = it.memo))
        }
        digitalForm.devicePasswordFormWrapper = deviceList
        digitalForm.emailFormWrapper = emailList
        digitalForm.websFormWrapper = webList
        digitalForm.id = findOneByAccount(account)!!.id
        digitalForm.password = findOneByAccount(account)!!.password
        return digitalForm
    }
    @Transactional
    fun save(digitalForm: DigitalForm, account: Account) {
        val device: MutableList<DevicePasswords> = mutableListOf()
        val email: MutableList<Emails> = mutableListOf()
        val webs: MutableList<Webs> = mutableListOf()
        val phonePass = PhoneCompanyPasswords(id = digitalForm.id, password = digitalForm.password, account = account)
        digitalForm.devicePasswordFormWrapper!!.forEach {
            device.add(DevicePasswords(name = it.name, password = it.password, account = account))
        }
        digitalForm.emailFormWrapper!!.forEach {
            email.add(Emails(email = it.email, password = it.password, account = account))
        }
        digitalForm.websFormWrapper!!.forEach {
            webs.add(Webs(name = it.name, webId = it.webId, password = it.password, memo = it.remarks, account = account))
        }
        phoneCompanyPasswordsRepository.deleteByAccount(account)
        if(digitalForm.devicePasswordFormWrapper!!.isNotEmpty()){
            devicePasswords.deleteByAccount(account)
        }
        if(digitalForm.emailFormWrapper!!.isNotEmpty()){
            emailsRepository.deleteByAccount(account)
        }
        if(digitalForm.websFormWrapper!!.isNotEmpty()){
            websRepository.deleteByAccount(account)
        }
        devicePasswords.save(device)
        phoneCompanyPasswordsRepository.save(phonePass)
        emailsRepository.save(email)
        websRepository.save(webs)
    }
}