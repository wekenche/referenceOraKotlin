package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.IndividualNoteCategory
import ora.leadlife.co.jp.repository.IndividualNoteCategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class IndividualNoteCategoryService {
    @Autowired
    lateinit var individualNoteCategoryRepository: IndividualNoteCategoryRepository

    fun findAllByAccountId(account: Account): List<IndividualNoteCategory> {
        return individualNoteCategoryRepository.findAllByAccountId(account.id!!.toLong())
    }

    fun findById(id: Long): IndividualNoteCategory? {
        return individualNoteCategoryRepository.findOne(id)
    }

    @Transactional
    fun save(individualNoteCategory: IndividualNoteCategory): IndividualNoteCategory? {
        return individualNoteCategoryRepository.save(individualNoteCategory)
    }

    fun delete(id: Long) {
        individualNoteCategoryRepository.delete(id)
    }

}