package danilovl.parameterbundle.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import danilovl.parameterbundle.Setting
import danilovl.parameterbundle.cache.ParameterIndex
import danilovl.parameterbundle.util.PhpUtil
import java.util.*

class ParameterCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val element = parameters.position

        val method = PhpUtil.resolveMethodReference(element)
        if (method === null) {
            return
        }

        val isInstanceOf = PhpUtil.isMethodReferenceInstanceOf(method, Setting.DEFAULT_PARAMETER_SERVICE_INTERFACE_NAMESPACE)
        if (!isInstanceOf) {
            return
        }

        val items = Collections.synchronizedList(mutableListOf<LookupElement>())
        ParameterIndex.getParameterMap(element.project).parameterMap.forEach { (key, value) ->
            items.add(buildLookup(key, value))
        }

        result.addAllElements(items.distinctBy { it.lookupString })
    }

    private fun buildLookup(key: String, value: String): LookupElement {
        val lookupElementBuilder = LookupElementBuilder
            .create(this, key)
            .withTypeText(value, true)
            .withIcon(null)

        return PrioritizedLookupElement.withGrouping(
            PrioritizedLookupElement.withPriority(lookupElementBuilder, 2.toDouble()),
            2
        )
    }
}
