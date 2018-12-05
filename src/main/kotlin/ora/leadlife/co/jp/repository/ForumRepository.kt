package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Forum
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ForumRepository : CrudRepository<Forum, Long> {
    @Query("SELECT t FROM Forum t ORDER BY t.id DESC")
    fun findAll(pageable: Pageable): Page<Forum>

    @Query("SELECT t FROM Forum t WHERE t.name LIKE %:keyword% OR t.contents LIKE %:keyword% ORDER BY t.id DESC")
    fun findByKeyword(pageable: Pageable, @Param("keyword") keyword: String?): Page<Forum>

    @Query("SELECT t FROM Forum t " +
            "WHERE t.title LIKE %:title% " +
            "AND t.contents LIKE %:contents% " +
            "AND t.name LIKE %:name% " +
            "OR t.id = (SELECT f.forumId FROM t.forumCommentList f WHERE f.contents LIKE %:reply%) ORDER BY t.id DESC")
    fun findByParams(pageable: Pageable,@Param("title") title: String?,@Param("contents") contents: String?,@Param("name") name: String?,@Param("reply") reply: String?) : Page<Forum>
}