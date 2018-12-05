package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/termsOfUse")

class TermsOfUseController {
    @GetMapping()
    fun termsOfUse(model: Model): String {
        return "termsOfUse/terms_of_use"
    }
}