package ora.leadlife.co.jp.controller.admin

import ora.leadlife.co.jp.form.SettingForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/setting")
class AdminSettingController {

    @Autowired lateinit var settingService: SettingService

    @GetMapping
    fun index(model: Model): String {
        if(settingService.finOne(1) != null) {
            //Only Setting data of ID 1 will be shown in the UI else none.
            val s = settingService.finOne(1)!!
            val f = SettingForm(id = s.id!! , email = s.email, delegate_tel_no = s.delegate_tel_no, organizer_email = s.organizer_email,
                    lawyer_email = s.lawyer_email, lawyer_tel_no = s.lawyer_tel_no,normalFee = s.normalFee, discountedFee = s.discountedFee,
                    tax = s.tax, veritransGroupId = s.veritransGroupId)
            model.addAttribute("form" , f)
        } else {
            model.addAttribute("form" , SettingForm())

        }
        model.addAttribute("navPage", "setting")
        return "admin/setting/index"
    }

    @PostMapping(path = arrayOf("/confirm"))
    fun confirm(model: Model,settingForm: SettingForm, @AuthenticationPrincipal user: User): String {
        settingService.save(settingForm)
        return "redirect:/admin/setting"
    }
}