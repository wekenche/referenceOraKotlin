package ora.leadlife.co.jp.config

import ora.leadlife.co.jp.helper.DiedNotification
import ora.leadlife.co.jp.helper.DiedNotificationDialect
import ora.leadlife.co.jp.helper.UploadedImage
import ora.leadlife.co.jp.helper.UploadedImageDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
class WebConfig : WebMvcConfigurerAdapter() {
    val map = mutableMapOf<String, Any>()

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>?) {
        val resolver = PageableHandlerMethodArgumentResolver()
        //ページ単位に表示する件数
        resolver.setFallbackPageable(PageRequest(0, 10))
        argumentResolvers!!.add(resolver)
        super.addArgumentResolvers(argumentResolvers)
    }

    private fun makeMap(): MutableMap<String, Any> {
        if (map.isEmpty()) {
            map["uploadedImage"] = uploadedImage()
            map["diedNotification"] = diedNotification()
        }
        return map
    }

    @Bean
    fun uploadedImageDialect(): UploadedImageDialect {
        return UploadedImageDialect(makeMap())
    }

    @Bean
    fun uploadedImage(): UploadedImage {
        return UploadedImage()
    }

    @Bean
    fun diedNotificationDialect(): DiedNotificationDialect {
        return DiedNotificationDialect(makeMap())
    }

    @Bean
    fun diedNotification(): DiedNotification {
        return DiedNotification()
    }
}