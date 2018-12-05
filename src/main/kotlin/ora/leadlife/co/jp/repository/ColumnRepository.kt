package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Category
import org.springframework.data.repository.CrudRepository

interface ColumnRepository : CrudRepository<Category, Long> {
}