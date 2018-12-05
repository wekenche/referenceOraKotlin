package ora.leadlife.co.jp.service
import ora.leadlife.co.jp.model.IndividualNoteBereave
import ora.leadlife.co.jp.repository.IndividualNoteBereaveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class IndividualNoteBereaveService {


    @Autowired
    lateinit var individualNoteBereaveRepository: IndividualNoteBereaveRepository

    fun findOne (id: Long) : IndividualNoteBereave {
        return individualNoteBereaveRepository.findOne(id)
    }
}