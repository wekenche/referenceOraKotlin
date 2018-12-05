package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.FuneralLists
import ora.leadlife.co.jp.repository.FuneralListsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.TransactionScoped
import javax.transaction.Transactional

@Service
class FuneralListService {
    @Autowired
    lateinit var funeralListsRepository: FuneralListsRepository

    fun findByAccount(account: Account) : List<FuneralLists> = funeralListsRepository.findByAccount(account)

    @TransactionScoped
    fun save(funeralLists: FuneralLists) : FuneralLists {
        return funeralListsRepository.save(funeralLists)
    }

    @Transactional
    fun delete(id: Long) {
        funeralListsRepository.delete(id)
    }
}