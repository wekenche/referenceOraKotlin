package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Funerals
import ora.leadlife.co.jp.repository.FuneralsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.TransactionScoped

@Service
class FuneralService {
    @Autowired
    lateinit var funeralRepository: FuneralsRepository

    fun findByAccount (account: Account) : List<Funerals>{
        return funeralRepository.findByAccount(account)
    }




    @TransactionScoped
    fun save(funerals: Funerals) : Funerals {
        return funeralRepository.save(funerals)
    }
}