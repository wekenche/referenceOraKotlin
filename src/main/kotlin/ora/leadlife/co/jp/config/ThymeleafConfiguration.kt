package ora.leadlife.co.jp.config

import ora.leadlife.co.jp.util.CustomDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ThymeleafConfiguration {
    @Bean
    internal fun myDialect(): CustomDialect {
        return CustomDialect()
    }
}