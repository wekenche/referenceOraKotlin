package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.SpecialistForTestaments
import ora.leadlife.co.jp.repository.SpecialistForTestamentsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpecialistForTestamentService {
    @Autowired lateinit var specialistForTestamentsRepository: SpecialistForTestamentsRepository
    fun findByAccount(account: Account) : List<SpecialistForTestaments> = specialistForTestamentsRepository.findByAccount(account)
}