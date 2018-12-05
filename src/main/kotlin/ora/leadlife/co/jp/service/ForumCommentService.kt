package ora.leadlife.co.jp.service

import ora.leadlife.co.jp.model.Forum
import ora.leadlife.co.jp.model.ForumComment
import ora.leadlife.co.jp.repository.ForumCommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ForumCommentService {
    @Autowired
    lateinit var forumCommentRepository: ForumCommentRepository

    fun findById(id: Long): ForumComment {
        return forumCommentRepository.findById(id)
    }

    fun findByForum(forum: Forum) : List<ForumComment> {
        return forumCommentRepository.findByForum(forum)
    }

    fun save(forumComment: ForumComment): ForumComment {
        return forumCommentRepository.save(forumComment)
    }

    @Transactional
    fun delete(id: Long) {
        val forumComment = forumCommentRepository.findOne(id)
        forumCommentRepository.delete(forumComment)
    }
}