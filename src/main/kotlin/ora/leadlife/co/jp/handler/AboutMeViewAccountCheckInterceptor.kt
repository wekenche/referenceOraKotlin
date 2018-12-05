package ora.leadlife.co.jp.handler

import ora.leadlife.co.jp.model.User
import ora.leadlife.co.jp.service.BereaveService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AboutMeViewAccountCheckInterceptor : HandlerInterceptor{

    @Autowired
    lateinit var bereaveService: BereaveService

    override fun postHandle(p0: HttpServletRequest?, p1: HttpServletResponse?, p2: Any?, p3: ModelAndView?) {
        if (p0 != null) {
            if (!p0.getParameter("aboutMeViewAccount").isNullOrBlank()) {
                p3!!.addObject("aboutMeViewAccount", p0.getParameter("aboutMeViewAccount"))
            }
        }
    }

    override fun afterCompletion(p0: HttpServletRequest?, p1: HttpServletResponse?, p2: Any?, p3: Exception?) {
    }

    override fun preHandle(p0: HttpServletRequest?, p1: HttpServletResponse?, p2: Any?): Boolean {
        if (p0 != null) {
            if (!p0.getParameter("aboutMeViewAccount").isNullOrBlank() && p0.getParameter("aboutMeViewAccount") != "null") {
                val user : User ? = SecurityContextHolder.getContext().authentication.principal as User
                if(user != null) {
                    if(!bereaveService.isBereave(p0.getParameter("aboutMeViewAccount").toLong() , user.lastUsedAccount!!.id!!)){
                        p1!!.sendRedirect("/")
                        return false
                    }
                }
            }
        }
        return true
    }
}