package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.ApproveForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.BereaveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Controller
@RequestMapping("/sharedAccount")
class SharedAccountController {

    @Autowired
    lateinit var bereaveService: BereaveService

    @Autowired
    lateinit var accountService: AccountService

    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("list", bereaveService.findByUser(user))
        return "sharedAccount/index"
    }

    @GetMapping(path = ["/detail/{id}"])
    fun detail(model: Model, @PathVariable id: String): String {
        if(accountService.findByIdToCheckNull(id) == null) {
            return "redirect:/"
        }
        val account = accountService.findById(id)
        model.addAttribute("account", account)
        model.addAttribute("title", account.user.name + "さんの情報")
        return "sharedAccount/detail"
    }

    @GetMapping(path = ["/approve/{id}"])
    fun approve(model: Model, @PathVariable id: String, @AuthenticationPrincipal user: User): String {
        val bereave = bereaveService.findById(id.toLong())
        if (bereave!!.user.id != user.id)
            return "redirect:/"
        model.addAttribute("bereave", bereave)
        val approveForm = ApproveForm()
        approveForm.id = id
        model.addAttribute(approveForm)
        return "sharedAccount/approve"
    }

    @PostMapping(path = ["/approve"])
    fun doApprove(model: Model, @Valid approveForm: ApproveForm, bindingResult: BindingResult): String {
        val result: String
        when {
            bindingResult.hasErrors() -> {
                result = "redirect:/"
            }
            else -> {
                val bereave = bereaveService.findById(approveForm.id.toLong())
                bereave!!.approval = true
                bereaveService.save(bereave)
                result = "redirect:/sharedAccount"
            }
        }
        return result
    }
}