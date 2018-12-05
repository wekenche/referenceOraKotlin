package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * 指紋登録
 */
@Controller
@RequestMapping("/fingerprintRegistration")
class FingerprintRegistrationController {
    @GetMapping("/before")
    fun before(model: Model): String {
        return "fingerprintRegistration/before"
    }
}