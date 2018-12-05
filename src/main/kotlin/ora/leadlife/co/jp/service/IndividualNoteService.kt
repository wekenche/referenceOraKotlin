package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.IndividualNote
import ora.leadlife.co.jp.repository.IndividualNoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class IndividualNoteService {

    @Autowired
    lateinit var individualNoteRepository: IndividualNoteRepository

    fun findById(id: Long): IndividualNote {
        return individualNoteRepository.findById(id)
    }

    fun findAll(): List<IndividualNote> {
        return individualNoteRepository.findAll().toList()
    }

    fun findByAccount(account: Account): List<IndividualNote> {
        return individualNoteRepository.findByAccount(account)
    }

    fun delete(id: Long) {
        individualNoteRepository.delete(id)
    }

    fun findByIndividualNoteCategoryIdAndAccount(individualNoteCategoryId: Long,account: Account): List<IndividualNote> {
        return individualNoteRepository.findByIndividualNoteCategoryIdAndAccount(individualNoteCategoryId,account)
    }

    fun findFirstByOrderByIdDesc():IndividualNote{
        return individualNoteRepository.findFirstByOrderByIdDesc()
    }

    /**
     * 保存
     */
    @Transactional
    fun save(individualNote: IndividualNote): IndividualNote {
        return individualNoteRepository.save(individualNote)
    }
}