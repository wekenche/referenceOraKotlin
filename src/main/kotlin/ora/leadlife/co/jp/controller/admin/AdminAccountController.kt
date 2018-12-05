package ora.leadlife.co.jp.controller.admin

import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.BereaveService
import ora.leadlife.co.jp.util.PageWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/account")
class AdminAccountController {
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var bereaveService: BereaveService

    @GetMapping
    fun index(model: Model, pageable: Pageable, @AuthenticationPrincipal user: User): String {
        model.addAttribute("account", accountService.findOneByUser(user))
        bereaves(model, pageable, user.lastUsedAccount!!)
        return "admin/account/index"
    }

    @GetMapping("/{id}")
    fun indexedId(model: Model, pageable: Pageable, @AuthenticationPrincipal user: User, @PathVariable id : String?): String {
        val account = accountService.findById(id!!)
        model.addAttribute("account", account)
        bereaves(model, pageable, account)
        return "admin/account/index"
    }

    private fun bereaves(model: Model, pageable: Pageable, account:Account) {
        val bereaves = bereaveService.findByAccount(account,pageable)
        val page = PageWrapper(bereaves, "/admin/account")
        model.addAttribute("page", page)
        model.addAttribute("bereaveList", page.content)
    }

    @PostMapping("/diedAdmin")
    fun diedConfirm(@AuthenticationPrincipal user: User): String {
        var account = user.lastUsedAccount
        account!!.diedConfirmedAdmin = true
        accountService.save(account)
        return "redirect:/admin/account"
    }
}