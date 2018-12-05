package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Shop
import ora.leadlife.co.jp.model.ShopPayHistory
import org.springframework.data.repository.CrudRepository

interface ShopPayHistoryRepository : CrudRepository<ShopPayHistory, Long> {
    fun findByShop(shop: Shop): ShopPayHistory
    fun findByShopAndPayed(shop: Shop, payed: Boolean): ShopPayHistory?
}