package ora.leadlife.co.jp.util

import org.thymeleaf.Arguments
import org.thymeleaf.dom.Element
import org.thymeleaf.processor.attr.AbstractUnescapedTextChildModifierAttrProcessor
import org.thymeleaf.standard.expression.StandardExpressionExecutionContext
import org.thymeleaf.standard.expression.StandardExpressions
import org.unbescape.html.HtmlEscape


/**
 * 改行コードをbrタグに変換するプロセッサ
 */
class TextLineProcessor : AbstractUnescapedTextChildModifierAttrProcessor(ATTR_NAME) {
    override fun getPrecedence(): Int {
        return ATTR_PRECEDENCE
    }

    /**
     * 出力する文字列を返す
     *
     * @param arguments
     * @param element
     * @param attributeName
     * @return
     */
    override fun getText(
            arguments: Arguments, element: Element, attributeName: String): String {
        var text = getAttributeObjectString(arguments, element, attributeName)
        // htmlエスケープ処理
        text = HtmlEscape.escapeHtml4Xml(text)

        // 改行コードをbrタグへ置換
        return text.replace("\r\n|\r|\n".toRegex(), "<br/>")
    }

    /**
     * 属性に指定された変数を文字列として取得する
     *
     * @param arguments
     * @param element
     * @param attributeName
     * @return
     */
    protected fun getAttributeObjectString(
            arguments: Arguments, element: Element, attributeName: String): String {
        val attributeValue = element.getAttributeValue(attributeName)
        val configuration = arguments.configuration
        val expressionParser = StandardExpressions.getExpressionParser(configuration)
        val expression = expressionParser.parseExpression(configuration, arguments, attributeValue)
        val result = expression.execute(configuration, arguments, StandardExpressionExecutionContext.UNESCAPED_EXPRESSION)

        return result?.toString() ?: ""
    }

    companion object {
        val ATTR_PRECEDENCE = 1450
        val ATTR_NAME = "text"
    }
}