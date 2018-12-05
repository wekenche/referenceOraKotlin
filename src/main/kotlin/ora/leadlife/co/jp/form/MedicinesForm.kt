package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class MedicinesForm(
        var id: Long? = 0,
        @get: NotBlank
        var name: String? = "",
        @get: NotBlank
        var day: String? = null,
        @get: NotBlank
        var month: String? = null,
        @get: NotBlank
        var year: String? = null,
        @get: NotBlank
        var hospital: String? = "",
        @get: NotBlank
        var doctor: String? = "",
        @get: NotBlank
        var medicineAmount: String? = "",
        @get: NotBlank
        var drinkHowTo: String? = "",
        @get: NotBlank
        var prescriptionDays: String? = ""
)