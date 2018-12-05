package ora.leadlife.co.jp.service

import com.sun.xml.internal.fastinfoset.stax.events.Util
import ora.leadlife.co.jp.model.Forum
import ora.leadlife.co.jp.repository.ForumCommentRepository
import ora.leadlife.co.jp.repository.ForumRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ForumService {
    @Autowired
    lateinit var forumRepository: ForumRepository

    @Autowired
    lateinit var forumCommentRepository: ForumCommentRepository

    fun getAllForum(pageable: Pageable, keyword: String? = null): Page<Forum> {
        // TODO keywordをSQLで振り分け
        return if (Util.isEmptyString(keyword)) forumRepository.findAll(pageable) else forumRepository.findByKeyword(pageable, keyword)
    }

    fun getAllSearch(pageable: Pageable,title: String? = null,contents: String? = null,name: String? = null,reply: String? = null) : Page<Forum> {
        return forumRepository.findByParams(pageable,title,contents,name,reply)
    }

    fun save(forum: Forum): Forum {
        return forumRepository.save(forum)
    }

    fun findById(id: Long): Forum {
        return forumRepository.findOne(id)
    }

    fun findById(id: String): Forum {
        return forumRepository.findOne(id.toLong())
    }

    fun updateCommentCount(forumId: Long): Forum {
        val forum = findById(forumId)
        forum.commentCount = forumCommentRepository.countByForumId(forumId).toInt()
        return save(forum)
    }

    fun delete(forum: Forum) {
        forumRepository.delete(forum)
    }

    fun findAll() : List<Forum> = forumRepository.findAll().toMutableList()
}