package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.NoteAttachment
import org.springframework.data.repository.CrudRepository

interface NoteAttachmentRepository : CrudRepository<NoteAttachment, Long> {
}