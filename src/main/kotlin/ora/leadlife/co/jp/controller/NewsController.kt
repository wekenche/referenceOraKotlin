package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.model.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/news")
class NewsController {

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "consultation/consultation_column"
    }
}