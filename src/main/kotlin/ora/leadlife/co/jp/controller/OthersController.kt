package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/others")
class OthersController {

    @GetMapping(path = arrayOf("/merit"))
    fun merit(model: Model): String {
        return "others/merit"
    }

    @GetMapping(path = arrayOf("/fingerprint/login"))
    fun fingerprintLogin(model: Model): String {
        return "others/fingerprint/login"
    }
}