package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.Size

data class PetsForm(
        @get: NotBlank
        @get: Size(max = 254)
        var name: String? = "",
        var isGender: Boolean = true,
        @get: NotBlank
        @get: Size(max = 254)
        var feed: String? = "",
        var isExistsPedigreeForm: Boolean = true,
        @get: NotBlank
        @get: Size(max = 254)
        var pedigreeFormNo: String? = "",

        var petsWrapper: List<PetsForm>? = null,
        var petsHospitalWrapper: List<HospitalPetsForm>? = null,
        var petsInsuranceWrapper: List<InsurancePetForm>? = null,
        var petsSalonWrapper: List<SalonForPetsForm>? = null
) {
    fun getIsExistsPedigreeForm(): Boolean {
        return this.isExistsPedigreeForm!!
    }

    fun setIsExistsPedigreeForm(isExistsPedigreeForm: Boolean) {
        this.isExistsPedigreeForm = isExistsPedigreeForm
    }

    fun getIsGender(): Boolean {
        return this.isGender!!
    }

    fun setIsGender(isGender: Boolean) {
        this.isGender = isGender
    }

}