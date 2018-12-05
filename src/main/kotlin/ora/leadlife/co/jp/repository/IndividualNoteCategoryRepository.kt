package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.IndividualNoteCategory
import org.springframework.data.repository.CrudRepository

interface IndividualNoteCategoryRepository : CrudRepository<IndividualNoteCategory, Long> {
    fun findAllByAccountId (accountId : Long) : List<IndividualNoteCategory>
}