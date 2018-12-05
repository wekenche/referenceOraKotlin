package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.IndividualNote
import ora.leadlife.co.jp.model.IndividualNoteAttachment
import org.springframework.data.repository.CrudRepository

interface IndividualNoteAttachmentRepository : CrudRepository<IndividualNoteAttachment, Long> {
    fun findOneByIndividualNote(individualNote:IndividualNote) : IndividualNoteAttachment
    fun findByIndividualNote (individualNote:IndividualNote) : List<IndividualNoteAttachment>
    fun findByIndividualNoteIn (individualNotes:List<IndividualNote>) : List<IndividualNoteAttachment>
}
