package ora.leadlife.co.jp.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean


@Configuration
class ValidationMessagesForUTF8 {
    @Bean(name = arrayOf("validator"))
    fun localValidatorFactoryBean(): LocalValidatorFactoryBean {
        val localValidatorFactoryBean = LocalValidatorFactoryBean()
        localValidatorFactoryBean.setValidationMessageSource(messageSource())
        return localValidatorFactoryBean
    }

    /**
     * ValidationのメッセージをUTF-8で管理します。
     * @return
     */
    @Bean(name = arrayOf("messageSource"))
    fun messageSource(): MessageSource {
        val bean = ReloadableResourceBundleMessageSource()
        bean.setBasename("classpath:ValidationMessages")
        bean.setDefaultEncoding("UTF-8")
        return bean
    }
}