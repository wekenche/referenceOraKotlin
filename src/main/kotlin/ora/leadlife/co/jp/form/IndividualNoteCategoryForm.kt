package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class IndividualNoteCategoryForm(
        @get: NotBlank
        var name: String = ""
)
