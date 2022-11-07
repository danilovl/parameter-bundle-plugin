package danilovl.parameterbundle.util

import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import danilovl.parameterbundle.cache.ParameterIndex
import java.util.*

class CompletionUtil {
    companion object {
         fun addCompletions(result: CompletionResultSet, project: Project) {
            val items = Collections.synchronizedList(mutableListOf<LookupElement>())
            ParameterIndex.getParameterMap(project).parameterMap.forEach { (key, value) ->
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
}
