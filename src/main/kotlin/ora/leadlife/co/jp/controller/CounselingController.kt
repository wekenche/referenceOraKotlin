package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/counseling")
class CounselingController {
    @GetMapping
    fun index(model: Model): String {
        return "counseling/index"
    }
}