package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.HealthsForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Medicines
import ora.leadlife.co.jp.repository.MedicinesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@Service
class MedicineService {

    @Autowired
    lateinit var medicineRepository: MedicinesRepository

    fun findByAccount(account: Account): List<Medicines> {
        return medicineRepository.findByAccount(account)
    }

    @Transactional
    fun save(healthsForm: HealthsForm, account: Account) {
        if (findByAccount(account).isNotEmpty()) {
            medicineRepository.deleteByAccount(account)
        }
        healthsForm.medicinesWrapper!!.forEach {
            var newDate: Date? = null
            if (it.year != "" && it.month != "" && it.day != "") {
                val date = it.year + "-" + it.month + "-" + it.day
                newDate = SimpleDateFormat("yyyy-MM-dd").parse(date)
            }
            medicineRepository.save(Medicines(id = it.id, name = it.name, prescriptionDate = newDate, hospital = it.hospital, doctor = it.doctor, medicineAmount = it.medicineAmount, drinkHowTo = it.drinkHowTo, prescriptionDays = it.prescriptionDays, account = account))
        }
    }
}