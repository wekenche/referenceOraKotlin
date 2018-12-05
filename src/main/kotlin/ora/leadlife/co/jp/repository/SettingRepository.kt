package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Setting
import org.springframework.data.repository.CrudRepository

/**
 * システム関連レポジトリ
 */
interface SettingRepository : CrudRepository<Setting, Long> {

}