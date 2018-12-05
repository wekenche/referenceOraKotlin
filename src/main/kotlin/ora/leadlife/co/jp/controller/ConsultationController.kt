package ora.leadlife.co.jp.controller

import ora.leadlife.co.jp.form.SettingForm
import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/consultation")
class ConsultationController {

    @Autowired lateinit var settingService: SettingService
    @GetMapping
    fun index(model: Model, @AuthenticationPrincipal user: User): String {
        if(settingService.finOne(1) != null) {
            //Only Setting data of ID 1 will be shown in the UI else none.
            var s = settingService.finOne(1)!!
            var f = SettingForm(id = s.id!! , email = s.email, delegate_tel_no = s.delegate_tel_no, organizer_email = s.organizer_email,
                    lawyer_email = s.lawyer_email, lawyer_tel_no = s.lawyer_tel_no)
            model.addAttribute("setting" , f)
        } else {
            model.addAttribute("setting" , SettingForm())
        }
        model.addAttribute("isPremium", user.lastUsedAccount!!.isPremium)
        return "consultation/consultation_index_new"
    }

    @GetMapping(path = arrayOf("/details"))
    fun column(model:Model):String{
        return "consultation/consultation_column"
    }
}