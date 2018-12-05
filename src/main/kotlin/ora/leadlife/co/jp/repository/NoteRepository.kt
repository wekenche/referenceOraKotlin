package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Note
import org.springframework.data.repository.CrudRepository

interface NoteRepository : CrudRepository<Note, Long> {
}