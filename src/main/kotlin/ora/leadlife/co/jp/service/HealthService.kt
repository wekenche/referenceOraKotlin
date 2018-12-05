package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.HealthsForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Healths
import ora.leadlife.co.jp.repository.HealthsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class HealthService {
    @Autowired
    lateinit var healthsRepository: HealthsRepository

    fun findByAccount(account: Account): Healths? {
        return healthsRepository.findByAccount(account)
    }

    @Transactional
    fun save(healthsForm: HealthsForm, account: Account) {
        healthsRepository.save(Healths(id = healthsForm.id, allergy = healthsForm.allergy!!, bloodType = healthsForm.bloodType, medicalHistory = healthsForm.medicalHistory, allergicExistsMedicine = healthsForm.allergicExistsMedicine, regularHospital = healthsForm.regularHospital, wantSignificantSickAnnouncement = healthsForm.wantSignificantSickAnnouncement, wantResuscitate = healthsForm.wantResuscitate, wantOrganDonation = healthsForm.wantOrganDonation, hasDonorCard = healthsForm.hasDonorCard, donorCardLocation = healthsForm.donorCardLocation, account = account))
    }
}