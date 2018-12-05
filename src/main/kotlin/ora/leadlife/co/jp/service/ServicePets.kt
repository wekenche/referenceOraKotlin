package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.PetsForm
import ora.leadlife.co.jp.model.*
import ora.leadlife.co.jp.repository.HospitalForPetsRepository
import ora.leadlife.co.jp.repository.InsuranceForPetsRepository
import ora.leadlife.co.jp.repository.PetsRepository
import ora.leadlife.co.jp.repository.SalonForPetsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServicePets {
    @Autowired
    lateinit var petsRepository: PetsRepository

    @Autowired
    lateinit var hospitalPetReposiory: HospitalForPetsRepository

    @Autowired
    lateinit var insurancePetRepository: InsuranceForPetsRepository

    @Autowired
    lateinit var salonPetRepository: SalonForPetsRepository

    fun findPetsByAccount(account: Account) : List<Pets> = petsRepository.findByAccount(account)
    fun findHospiByAccount(account: Account) : List<HospitalForPets> = hospitalPetReposiory.findByAccount(account)
    fun findInsuranceByAccount(account: Account) : List<InsuranceForPets> = insurancePetRepository.findByAccount(account)
    fun findSalonByAccount(account: Account) : List<SalonForPets> = salonPetRepository.findByAccount(account)

    @Transactional
    fun save(petsForm: PetsForm, account: Account) {
        var pets: MutableList<Pets> = mutableListOf()
        var hospital: MutableList<HospitalForPets> = mutableListOf()
        var insurance: MutableList<InsuranceForPets> = mutableListOf()
        var salon: MutableList<SalonForPets> = mutableListOf()
        if(petsRepository.findByAccount(account).isNotEmpty()) {
            petsRepository.deleteByAccount(account)
        }
        petsForm.petsWrapper!!.forEach {
            pets.add(Pets(name = it.name,gender = it.isGender, feed = it.feed, existsPedigreeForm = it.isExistsPedigreeForm, pedigreeFormNo = it.pedigreeFormNo, account = account))
        }
        if(hospitalPetReposiory.findByAccount(account).isNotEmpty()) {
            hospitalPetReposiory.deleteByAccount(account)
        }
        petsForm.petsHospitalWrapper!!.forEach {
            hospital.add(HospitalForPets(diseaseName = it.diseaseName, hospital = it.hospital, contactAddress = it.contactAddress, account = account))
        }
        if(insurancePetRepository.findByAccount(account).isNotEmpty()) {
            insurancePetRepository.deleteByAccount(account)
        }
        petsForm.petsInsuranceWrapper!!.forEach {
            insurance.add(InsuranceForPets(company = it.company, tel = it.tel, billingMethod = it.billingMethod, memo = it.remarks, account = account))
        }
        if(salonPetRepository.findByAccount(account).isNotEmpty()) {
            salonPetRepository.deleteByAccount(account)
        }
        petsForm.petsSalonWrapper!!.forEach {
            salon.add(SalonForPets(name = it.name, contactAddress = it.contactAddress, account = account))
        }
        petsRepository.save(pets)
        hospitalPetReposiory.save(hospital)
        insurancePetRepository.save(insurance)
        salonPetRepository.save(salon)
    }
}