package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/")
class TopController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var accountService: AccountService

    @RequestMapping("/")
    fun index(model: Model, redirectAttributes: RedirectAttributes, @AuthenticationPrincipal user: User, @RequestHeader(value = "User-Agent") header : String): String {
        if(user.isValid) {
            if (user.lastUsedAccount!!.isPremium)
                model.addAttribute("imageName", "")
            else
                model.addAttribute("imageName", "_off")
            model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
            model.addAttribute("lastUsedAccountId", user.lastUsedAccount!!.id)
            val os = if (header.toLowerCase().indexOf("android") >= 1) {
                "ANDROID"
            } else if (header.toLowerCase().indexOf("iphone") >= 1) {
                "IOS"
            } else {
                "OTHER"
            }
            user.os = os
            userService.save(user)
            if (!checkIsPremium(user))
                return "redirect:/"
        }else{
            redirectAttributes.addFlashAttribute("isValid", "登録されたメールアドレスに承認依頼のメールを送信しておりますので、ご確認ください。")
            return "redirect:/login/input"
        }
        return "index"
    }

    private fun checkIsPremium(user: User): Boolean {
        val nowAccount = user.lastUsedAccount!!
        val account = accountService.findById(nowAccount.id!!)
        val result = nowAccount.isPremium == account.isPremium
        nowAccount.isPremium = account.isPremium
        return result
    }
}