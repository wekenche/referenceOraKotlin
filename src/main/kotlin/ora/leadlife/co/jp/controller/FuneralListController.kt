package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.service.FuneralListService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class FuneralListController {
    @Autowired
    lateinit var funeralListService: FuneralListService

    @DeleteMapping("/funeral/list/delete/{id}")
    fun deleteFuneralList(@PathVariable id: String): MutableMap<String, String>? {
        funeralListService.delete(id.toLong())
        return Collections.singletonMap("result", "ok")
    }
}