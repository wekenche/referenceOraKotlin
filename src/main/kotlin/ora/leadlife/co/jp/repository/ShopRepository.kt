package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Shop
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

/**
 * 代理店テーブル用リポジトリ
 */
interface ShopRepository : CrudRepository<Shop, Long> {
    @Query("SELECT t FROM Shop t ORDER BY t.id DESC")
    fun findAll(pageable: Pageable): Page<Shop>

    @Query("SELECT t FROM Shop t WHERE t.name LIKE %:keyword% OR t.code LIKE %:keyword% ORDER BY t.id DESC")
    fun findByKeyword(pageable: Pageable, @Param("keyword") keyword: String?): Page<Shop>

    @Query("SELECT s FROM Shop s WHERE s.name LIKE %?1%  AND s.code LIKE %?2% " +
            "AND TRIM(s.backPercent) LIKE %?3% " +
            "AND TRIM(s.backParentPercent) LIKE %?4% ORDER BY s.id DESC")
    fun findByNameAndCodeAndParentCodeAndBackPercentAndBackParentPercent(pageable: Pageable,name : String?,code : String?,
             backParent : String?,backParentPercent : String?) : Page<Shop>

    @Modifying
    @Query("UPDATE Shop s set s.parentId = :parentId WHERE s.id = :shop_id")
    fun updateParentShop(@Param("parentId") parentId : Long?,@Param("shop_id") shop_id : Long?): Int

    /**
     * 紹介コードから検索
     * @param code 紹介コード
     * @return 検索結果
     */
    fun findByCode(code: String): Shop?

    fun findById(id: Long): Shop?

    fun findByParent(shop : Shop) : List<Shop>
}