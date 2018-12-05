package ora.leadlife.co.jp.service
import ora.leadlife.co.jp.model.IndividualNote
import ora.leadlife.co.jp.model.IndividualNoteAttachment
import ora.leadlife.co.jp.repository.IndividualNoteAttachmentRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import javax.transaction.Transactional

@Service
class IndividualNoteAttachmentService {

    @Autowired
    lateinit var individualNoteAttachmentRepository: IndividualNoteAttachmentRepository

    val logger: Logger = LoggerFactory.getLogger(IndividualNoteAttachmentService::class.java)

    fun findOne (id : Long) : IndividualNoteAttachment {
        return individualNoteAttachmentRepository.findOne(id)
    }

    fun findOneByIndividualNote(individualNote: IndividualNote):IndividualNoteAttachment {
        return individualNoteAttachmentRepository.findOneByIndividualNote(individualNote)
    }

    fun findByIndividualNote(individualNote: IndividualNote): List<IndividualNoteAttachment> {
        return individualNoteAttachmentRepository.findByIndividualNote(individualNote)
    }

    fun findByIndividualNoteIn(individualNote: List<IndividualNote>): List<IndividualNoteAttachment> {
        return individualNoteAttachmentRepository.findByIndividualNoteIn(individualNote)
    }

    @Transactional
    fun save(individualNoteAttachment: IndividualNoteAttachment){
        individualNoteAttachmentRepository.save(individualNoteAttachment)
    }

    @Transactional
    fun delete(id: Long) {
        val individualNoteAttachment = individualNoteAttachmentRepository.findOne(id)
        try {
            val file = File(individualNoteAttachment.filePath)
            if (file.exists())
                file.delete()
        } catch (e: Exception) {
            logger.error(e.localizedMessage)
        }
        individualNoteAttachmentRepository.delete(id)
    }
}