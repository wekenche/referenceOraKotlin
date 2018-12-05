package ora.leadlife.co.jp.controller.admin

import ora.leadlife.co.jp.model.Forum
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.ForumCommentService
import ora.leadlife.co.jp.service.ForumService
import ora.leadlife.co.jp.util.PageWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/forum")
class AdminForumController {

    @Autowired lateinit var forumService: ForumService
    @Autowired lateinit var forumCommentService: ForumCommentService

    fun initData(model: Model, name: String?, title: String?,content: String?,reply: String?) {
        model.addAttribute("sname",name)
        model.addAttribute("stitle", title)
        model.addAttribute("scontent", content)
        model.addAttribute("sreply", reply)
        model.addAttribute("navPage", "forum")
    }
    @GetMapping
    fun index(model: Model, pageable: Pageable): String {
        val page = PageWrapper(forumService.getAllForum(pageable), "/admin/forum")
        model.addAttribute("list",page.content)
        model.addAttribute("page", page)
        initData(model,"","","","")
        return "admin/forum/index"
    }

    @GetMapping("/search")
    fun search(model: Model, pageable: Pageable ,
               @RequestParam(value = "title", required = false) title : String?,
               @RequestParam(value = "name",required = false) name : String?,
               @RequestParam(value = "contents",required = false) content : String?,
               @RequestParam(value = "reply",required = false) reply : String?): String {
        var forums = forumService.getAllSearch(pageable,title,content,name,reply)
        val page = PageWrapper(forums, "/admin/forum")
        model.addAttribute("list",page.content)
        model.addAttribute("page", page)
        initData(model,name,title,content,reply)
        return "admin/forum/index"
    }

    @GetMapping("/comment/{id}")
    fun comment(model: Model, @PathVariable id: String): String {
        val forum: Forum = forumService.findById(id)
        model.addAttribute("comments",forumCommentService.findByForum(forum))
        return "admin/forum/comment"
    }

    @DeleteMapping(path = arrayOf("/delete/{id}"))
    @ResponseBody
    fun deleteForum(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        forumService.delete(forumService.findById(id.toLong()))
        return "/admin/forum"
    }

    @DeleteMapping(path = arrayOf("/comment/delete/{id}"))
    @ResponseBody
    fun deleteComment(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        forumCommentService.delete(id.toLong())
        return "/admin/forum"
    }
}