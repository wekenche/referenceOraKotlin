package ora.leadlife.co.jp.controller.admin

import ora.leadlife.co.jp.config.Role
import ora.leadlife.co.jp.service.UserService
import ora.leadlife.co.jp.util.PageWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/admin/user")
class AdminUserController {
    @Autowired
    lateinit var  userService: UserService

    @GetMapping
    fun index(model: Model,pageable: Pageable): String {
        val page = PageWrapper(userService.getAll(pageable), "/admin/user")
        model.addAttribute("users",page.content)
        model.addAttribute("page", page)
        model.addAttribute("navPage", "user")
        return "admin/user/index"
    }

    @GetMapping("search")
    fun search(model: Model, pageable: Pageable,
               @RequestParam(value = "name" , required = false) name : String?,
               @RequestParam(value = "email" , required = false) email : String?,
               @RequestParam(value = "shop" , required = false) shop : String?,
               @RequestParam(value = "paid" , required = false) paid : Boolean?,
               @RequestParam(value = "user_type" , required = false) userType : String?
    ): String {
        var isPaid = true
        if(paid == null) {
            isPaid = false
        }
        val page = PageWrapper(userService.findByParams(pageable,name,email,shop,isPaid,userType), "/admin/user")
        model.addAttribute("users", page.content)
        model.addAttribute("page", page)
        model.addAttribute("sname", name)
        model.addAttribute("semail", email)
        model.addAttribute("sshop", shop)
        model.addAttribute("spaid", paid)
        
        return "admin/user/index"
    }

    @PatchMapping("changeType/{id}")
    @ResponseBody
    fun changeType(@PathVariable id: String) {
        val user = userService.findById(id.toLong())

        if(user.userType == Role.ADMIN.name)
            user.userType = Role.USER.name
        else
            user.userType = Role.ADMIN.name

        userService.save(user)
    }
}