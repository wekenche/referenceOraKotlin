package ora.leadlife.co.jp.helper

import org.thymeleaf.context.IProcessingContext
import org.thymeleaf.dialect.AbstractDialect
import org.thymeleaf.dialect.IExpressionEnhancingDialect

class UploadedImageDialect(var expressionObjects: MutableMap<String, Any>? = null) : AbstractDialect(), IExpressionEnhancingDialect {


    override fun getPrefix(): String? {
        return null
    }

    override fun getAdditionalExpressionObjects(p0: IProcessingContext?): MutableMap<String, Any>? {
        return expressionObjects
    }

}