package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.NoteCategory
import org.springframework.data.repository.CrudRepository

interface NoteCategoryRepository : CrudRepository<NoteCategory, Long> {
}