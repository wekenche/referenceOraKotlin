package ora.leadlife.co.jp.form

import org.hibernate.validator.constraints.NotBlank

data class IndividualNoteForm(
        var individualId: Long= 0,
        @get: NotBlank
        var individualNoteCategory: Long = 0,
        @get: NotBlank
        var messageType: String = "NONE",
        @get: NotBlank
        var individualTitle: String = "",
        @get: NotBlank
        var individualNoteContents: String = "",
        var individualNoteAttachment: String = ""
) {

}