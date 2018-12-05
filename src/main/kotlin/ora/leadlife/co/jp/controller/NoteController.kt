package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * エンディングノート
 */
@Controller
@RequestMapping("/note")
class NoteController {
    @GetMapping(path = arrayOf("/index"))
    fun index(model: Model): String {
        return "note/index"
    }

    @GetMapping(path = arrayOf("/detail"))
    fun detail(model: Model): String {
        return "note/detail"
    }

    @GetMapping(path = arrayOf("/input"))
    fun input(model: Model): String {
        return "note/input"
    }

    @PostMapping(path = arrayOf("/update"))
    fun update(model: Model): String {
        return "note/update"
    }

    @PostMapping(path = arrayOf("/delete"))
    fun delete(model: Model): String {
        return "note/delete"
    }
}