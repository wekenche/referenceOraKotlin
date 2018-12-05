package ora.leadlife.co.jp.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * for smartPhone
 */
@Controller
@RequestMapping("/smartPhone")
class SmartPhoneController {
    val logger: Logger = LoggerFactory.getLogger(SmartPhoneController::class.java)

    @GetMapping
    fun path(@RequestParam path: String): String {
        logger.info("path = $path")
        return "redirect:seizenseiri://?path=$path"
    }
}