package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.FuneralForm
import ora.leadlife.co.jp.form.FuneralListForm
import ora.leadlife.co.jp.model.Account
import ora.leadlife.co.jp.model.FuneralLists
import ora.leadlife.co.jp.model.Funerals
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.AccountService
import ora.leadlife.co.jp.service.FuneralService
import ora.leadlife.co.jp.service.FuneralListService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/funeral")
class FuneralController {

    @Autowired
    lateinit var funeralService: FuneralService

    @Autowired
    lateinit var funeralListService: FuneralListService

    @Autowired lateinit var accountService: AccountService


    @GetMapping
    fun index(model: Model): String {
        return "funeral/index"
    }


    @GetMapping(path = arrayOf("/add"))
    fun add(model: Model, @AuthenticationPrincipal user: User, @RequestParam(required=false) aboutMeViewAccount :String?): String {
        var account: Account = user.lastUsedAccount!!
        if(aboutMeViewAccount.isNullOrEmpty()){
            setPage(account,model,0)
        } else {
            account = accountService.findById(aboutMeViewAccount.toString())
            setPage(account, model, 1)
        }
        return "funeral/add"
    }

    fun setPage(account: Account, model: Model, view : Int) {
        val funeralLists : ArrayList<FuneralListForm> = ArrayList()
        if(funeralListService.findByAccount(account).isNotEmpty()) {

            funeralListService.findByAccount(account).forEach {
                funeralLists.add(
                    FuneralListForm(
                        funeralListId = it.id!!,
                        funeralListName = it.name!!,
                        funeralListPostCode1 = it.postCode1!!,
                        funeralListPostCode2 = it.postCode2!!,
                        funeralListAddress = it.address!!,
                        funeralListTel = it.tel!!
                    )
                )
            }
        }

        val funerals = funeralService.findByAccount(account)
        val funeralForm = FuneralForm()
        if(funerals.isNotEmpty()) {
            val funeral = funerals.first()
            funeralForm.funeralId = funeral.id!!
            funeralForm.funeralCompany = funeral.company!!
            funeralForm.funeralContactAddress = funeral.contactAddress!!
            funeralForm.funeralFee = funeral.funeralFee!!
            funeralForm.isReservation = funeral.isReservation!!
            funeralForm.funeralReligionType = funeral.religionType!!
            funeralForm.memo = funeral.memo!!
            funeralForm.funeralListWrapper = funeralLists
        }

        model.addAttribute("funeralForm" , funeralForm)

        //////////////////////////////////////////////
        model.addAttribute("aboutMeViewFlag", view)
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model, funeralForm: FuneralForm, @AuthenticationPrincipal user: User): String {
        funeralService.save(Funerals(
                id = funeralForm.funeralId,
                account = user.lastUsedAccount!!,
                company = funeralForm.funeralCompany,
                contactAddress = funeralForm.funeralContactAddress,
                funeralFee = funeralForm.funeralFee,
                isReservation = funeralForm.isReservation,
                religionType = funeralForm.funeralReligionType,
                memo = funeralForm.memo
            )
        )

        val funeralLists = funeralForm.funeralListWrapper!!
        funeralLists.forEach {
            if(it.funeralListName.isNotEmpty()) {
                funeralListService.save(
                        FuneralLists(
                                id = it.funeralListId,
                                account = user.lastUsedAccount!!,
                                name = it.funeralListName,
                                address = it.funeralListAddress,
                                postCode1 = it.funeralListPostCode1,
                                postCode2 = it.funeralListPostCode2,
                                tel = it.funeralListTel
                        )
                )
            }
        }

        return "redirect:/funeral/add"
    }
}