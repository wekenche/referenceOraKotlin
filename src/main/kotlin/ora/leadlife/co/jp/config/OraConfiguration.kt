package ora.leadlife.co.jp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "ora")
data class OraConfiguration(
        var mailType: String = ""
)