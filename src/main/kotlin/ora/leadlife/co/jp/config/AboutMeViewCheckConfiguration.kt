package ora.leadlife.co.jp.config

import ora.leadlife.co.jp.handler.AboutMeViewAccountCheckInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.handler.MappedInterceptor

@Configuration
class AboutMeViewCheckConfiguration {

    @Bean
    fun aboutMeViewAccountCheckInterceptor(): AboutMeViewAccountCheckInterceptor {
        return AboutMeViewAccountCheckInterceptor()
    }

    @Bean
    fun interceptor(): MappedInterceptor {
        return MappedInterceptor(arrayOf("/**"), aboutMeViewAccountCheckInterceptor())
    }
}