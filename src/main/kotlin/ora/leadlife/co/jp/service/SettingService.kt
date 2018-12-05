package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.SettingForm
import ora.leadlife.co.jp.model.Setting
import ora.leadlife.co.jp.repository.SettingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SettingService {
    @Autowired
    lateinit var settingRepository: SettingRepository

    fun getSystem(): Setting {
        var setting: Setting? = null
        settingRepository.findAll().forEach { setting = it }
        return setting!!
    }

    fun finOne(id: Long): Setting? = settingRepository.findOne(id)

    @Transactional
    fun save(s: SettingForm) {
        settingRepository.save(Setting(id = s.id, email = s.email, delegate_tel_no = s.delegate_tel_no,
                organizer_email = s.organizer_email, lawyer_email = s.lawyer_email, lawyer_tel_no = s.lawyer_tel_no
                ,normalFee = s.normalFee, discountedFee = s.discountedFee, tax = s.tax, veritransGroupId = s.veritransGroupId  ))
    }

    fun findFirst(): Setting {
        return settingRepository.findAll().first()
    }
}