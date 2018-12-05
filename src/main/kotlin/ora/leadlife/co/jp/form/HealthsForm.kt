package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.validation.constraints.Size

data class HealthsForm(
        var id: Long? = 0,
        @get: NotBlank
        var allergy: String? = "",
        @get: NotBlank
        @get: Size(max = 10)
        var bloodType: String? = "",
        @get: NotBlank
        var medicalHistory: String? ="",
        @get: NotBlank
        var allergicExistsMedicine: String? ="",
        @get: NotBlank
        var regularHospital: String? ="",
        @get: NotBlank
        var wantSignificantSickAnnouncement: Boolean = false,
        @get: NotBlank
        var wantResuscitate: Boolean = false,
        @get: NotBlank
        var wantOrganDonation: Boolean = false,
        @get: NotBlank
        var hasDonorCard: Boolean = false,
        @get: NotBlank
        var donorCardLocation: String? = "",

        var file:Array<MultipartFile>? = null,

        var medicinesWrapper: List<MedicinesForm>? = null
){
    fun getIsWantSignificantSickAnnouncement(): Boolean {
        return this.wantSignificantSickAnnouncement!!
    }

    fun setIsWantSignificantSickAnnouncement(wantSignificantSickAnnouncement: Boolean) {
        this.wantSignificantSickAnnouncement = wantSignificantSickAnnouncement
    }
    fun getIsWantResuscitate(): Boolean {
        return this.wantResuscitate!!
    }

    fun setIsWantResuscitate(wantResuscitate: Boolean) {
        this.wantResuscitate = wantResuscitate
    }
    fun getIsWantOrganDonation(): Boolean {
        return this.wantOrganDonation!!
    }

    fun setIsWantOrganDonation(wantOrganDonation: Boolean) {
        this.wantOrganDonation = wantOrganDonation
    }
    fun getIsHasDonorCard(): Boolean {
        return this.hasDonorCard!!
    }

    fun setIsHasDonorCard(hasDonorCard: Boolean) {
        this.hasDonorCard = hasDonorCard
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HealthsForm

        if (!Arrays.equals(file, other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        return file?.let { Arrays.hashCode(it) } ?: 0
    }

}