package ora.leadlife.co.jp.util

import org.thymeleaf.dialect.AbstractDialect
import org.thymeleaf.processor.IProcessor
import java.util.*

/**
 * 独自定義したダイアレクトを登録する
 */
class CustomDialect : AbstractDialect() {
    override fun getPrefix(): String {
        return DIALECT_PREFIX
    }

    override fun getProcessors(): Set<IProcessor> {
        val processors = HashSet<IProcessor>()
        processors.add(TextLineProcessor())
        return processors
    }

    companion object {
        internal val DIALECT_PREFIX = "ex"
    }
}