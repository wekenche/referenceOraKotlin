package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.form.TreasuresForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.Treasures
import ora.leadlife.co.jp.repository.TreasuresRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TreasuresService {

    @Autowired
    lateinit var treasuresRepository: TreasuresRepository

    fun findByAccount(account: Account):List<Treasures>{
        return treasuresRepository.findByAccount(account)
    }
    @Transactional
    fun save(treasuresForm: TreasuresForm, account: Account){
        if(findByAccount(account).isNotEmpty()){
            treasuresRepository.deleteByAccount(account)
        }
        treasuresForm.treasureWrapper!!.forEach{
            treasuresRepository.save(Treasures(name = it.name, storingPlace = it.storingPlace, processingMethod = it.processingMethod, memo = it.remarks, account = account,accountId = account.id ))
        }
    }
}