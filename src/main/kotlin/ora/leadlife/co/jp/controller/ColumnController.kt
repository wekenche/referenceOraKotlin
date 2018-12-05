package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/column")
class ColumnController {
    @GetMapping
    fun index(model: Model): String {
        return "column/index"
    }
}