package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.ShopPayHistoryPayment
import org.springframework.data.repository.CrudRepository

interface ShopPayHistoryPaymentRepository : CrudRepository<ShopPayHistoryPayment, Long> {
}