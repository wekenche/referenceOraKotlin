package ora.leadlife.co.jp

import org.apache.catalina.connector.Connector
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class OraApplication {
    @Bean
    fun servletContainer(): EmbeddedServletContainerFactory {

        val tomcat = TomcatEmbeddedServletContainerFactory()

        val ajpConnector = Connector("AJP/1.3")
        ajpConnector.setProtocol("AJP/1.3")
        ajpConnector.setPort(9090)
        ajpConnector.setSecure(false)
        ajpConnector.setAllowTrace(false)
        ajpConnector.setScheme("http")
        tomcat.addAdditionalTomcatConnectors(ajpConnector)

        return tomcat
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(OraApplication::class.java, *args)
}

