package ora.leadlife.co.jp.service

import com.sun.xml.internal.fastinfoset.stax.events.Util
import ora.leadlife.co.jp.model.Shop
import ora.leadlife.co.jp.repository.ShopRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ShopService {
    @Autowired
    lateinit var shopRepository: ShopRepository

    fun getAll(pageable: Pageable, keyword: String? = null): Page<Shop> {
        return if (Util.isEmptyString(keyword))
            shopRepository.findAll(pageable)
        else
            shopRepository.findByKeyword(pageable, keyword)
    }

    fun findByParams(pageable: Pageable,name : String?, code : String?, back : String?, backParent : String?): Page<Shop> {
        return shopRepository.findByNameAndCodeAndParentCodeAndBackPercentAndBackParentPercent(pageable,name,code,back,backParent)
    }

    fun findByCode(code: String): Shop? {
        return shopRepository.findByCode(code)
    }

    fun findById(id: Long): Shop? {
        return shopRepository.findById(id)
    }

    fun findByParent(shop : Shop) : List<Shop> {
        return shopRepository.findByParent(shop)
    }

    @Transactional
    fun updateParentShop(parentId: Long?,shopId: Long?) : Int {
        return shopRepository.updateParentShop(parentId,shopId)
    }

    fun findAll(): List<Shop> = shopRepository.findAll().toMutableList()

    @Transactional
    fun save(shop: Shop): Shop {
        return shopRepository.save(shop)
    }

    @Transactional
    fun delete(id: Long) {
        val shop = shopRepository.findOne(id)
        shopRepository.delete(shop)
    }
}