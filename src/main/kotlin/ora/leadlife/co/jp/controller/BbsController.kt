package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.ForumCommentForm
import ora.leadlife.co.jp.form.ForumForm
import ora.leadlife.co.jp.model.Forum
import ora.leadlife.co.jp.model.ForumComment
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.ForumCommentService
import ora.leadlife.co.jp.service.ForumService
import ora.leadlife.co.jp.util.PageWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 掲示板用
 */
@Controller
@RequestMapping("/bbs")
class BbsController {
    @Autowired
    lateinit var forumService: ForumService

    @Autowired
    lateinit var forumCommentService: ForumCommentService

    @GetMapping
    fun index(model: Model, pageable: Pageable, @AuthenticationPrincipal user: User,
              @RequestParam(required = false) keyword: String?): String {
        val forumForm = ForumForm()
        if (user.lastUsedAccount != null) forumForm.name = user.name!!
        findForums(model, pageable, forumForm, keyword)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "bbs/index"
    }

    @PostMapping(path = arrayOf("/write"))
    fun write(@Valid forumForm: ForumForm, bindingResult: BindingResult, request: HttpServletRequest, model: Model,
              pageable: Pageable, @AuthenticationPrincipal user: User, @RequestParam(required = false) keyword: String?): String {
        if (bindingResult.hasErrors()) {
            findForums(model, pageable, forumForm, keyword)
            return "bbs/index"
        }

        val forum = Forum()
        forum.title = forumForm.title
        forum.name = forumForm.name
        forum.contents = forumForm.contents
        forum.accountId = user.lastUsedAccount!!.id!!
        forumService.save(forum)

        return "redirect:/bbs/done"
    }

    @GetMapping(path = arrayOf("/done"))
    fun done(model: Model): String {
        return "bbs/done"
    }

    @GetMapping(path = arrayOf("/delete/{id}"))
    fun delete(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        val forum: Forum = forumService.findById(id)
        if (user.lastUsedAccount != null && forum.accountId == user.lastUsedAccount!!.id) {
            forumService.delete(forum)
            model.addAttribute("message", "削除しました。")
            return "redirect:/bbs"
        }
        model.addAttribute("message", "不正なアクセスです。")
        return "redirect:bbs/detail/$id"
    }

    @GetMapping(path = arrayOf("/detail/{id}"))
    fun detail(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        val forum: Forum = forumService.findById(id)
        model.addAttribute("user", user)
        model.addAttribute("forum", forum)
        model.addAttribute("forumComments", forum.forumCommentList)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)

        return "bbs/detail"
    }

    @GetMapping(path = arrayOf("/reply/{id}"))
    fun replay(model: Model, @AuthenticationPrincipal user: User, @PathVariable id: String): String {
        val forumComment = ForumCommentForm(forumId = id)
        if (user.lastUsedAccount != null) forumComment.name = user.name!!
        model.addAttribute("forumCommentForm", forumComment)
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "bbs/reply"
    }

    @PostMapping(path = arrayOf("/writeReply"))
    fun writeReply(@Valid forumCommentForm: ForumCommentForm, bindingResult: BindingResult, request: HttpServletRequest, model: Model,
                   @AuthenticationPrincipal user: User): String {
        if (bindingResult.hasErrors()) {
            return "bbs/reply"
        }

        val forumComment = ForumComment()
        val forum: Forum = forumService.findById(forumCommentForm.forumId)
        forumComment.name = forumCommentForm.name
        forumComment.contents = forumCommentForm.contents
        forumComment.forumId = forumCommentForm.forumId.toLong()
        forumComment.forum = forum
        forumComment.account = user.lastUsedAccount!!
        forumCommentService.save(forumComment)
        forumService.updateCommentCount(forumComment.forumId)

        return "redirect:/bbs/detail/${forumCommentForm.forumId}"
    }

    private fun findForums(model: Model, pageable: Pageable, forumForm: ForumForm, keyword: String?) {
        val forumPage = forumService.getAllForum(pageable, keyword)
        val page = PageWrapper(forumPage, "/bbs")
        model.addAttribute("page", page)
        model.addAttribute("forums", page.content)
        model.addAttribute("forumForm", forumForm)
    }
}