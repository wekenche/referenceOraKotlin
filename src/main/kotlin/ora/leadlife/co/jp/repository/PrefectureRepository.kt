package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Prefecture
import org.springframework.data.repository.CrudRepository

interface PrefectureRepository : CrudRepository<Prefecture, Long> {
}