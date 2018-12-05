package ora.leadlife.co.jp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/aboutMe")

class AboutMeTemplateController {

    @GetMapping("propertyAboutTop")
    fun propertyAboutTop(): String {
        return "aboutMe/property_about_top"
    }

    @GetMapping("propertyAboutAssets")
    fun propertyAboutAssets(): String {
        return "aboutMe/property_about_assets"
    }

    @GetMapping("funeralAboutTop")
    fun funeralAboutTop(): String {
        return "aboutMe/funeral_about_top"
    }


}