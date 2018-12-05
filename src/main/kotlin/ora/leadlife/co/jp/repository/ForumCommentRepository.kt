package ora.leadlife.co.jp.repository

import ora.leadlife.co.jp.model.Forum
import ora.leadlife.co.jp.model.ForumComment
import org.springframework.data.repository.CrudRepository

interface ForumCommentRepository : CrudRepository<ForumComment, Long> {
    fun findById(id: Long): ForumComment

    fun countByForumId(id: Long): Long

    fun findByForum(forum: Forum): List<ForumComment>
}