package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.IndividualNote
import org.springframework.data.repository.CrudRepository

interface IndividualNoteRepository : CrudRepository<IndividualNote, Long> {
    fun findById(id: Long): IndividualNote

    fun findByAccount(account: Account): List<IndividualNote>

    fun findByIndividualNoteCategoryIdAndAccount(individualNoteCategoryId: Long,account:Account): List<IndividualNote>

    fun findFirstByOrderByIdDesc():IndividualNote
}