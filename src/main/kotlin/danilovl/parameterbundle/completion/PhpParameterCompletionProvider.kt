package danilovl.parameterbundle.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.util.ProcessingContext
import danilovl.parameterbundle.Setting
import danilovl.parameterbundle.util.CompletionUtil
import danilovl.parameterbundle.util.PhpUtil

class PhpParameterCompletionProvider : CompletionProvider<CompletionParameters>() {
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

        val isInstanceOf = PhpUtil.isMethodReferenceInstanceOf(method, Setting.PARAMETER_SERVICE_INTERFACE_NAMESPACE)
        if (!isInstanceOf) {
            return
        }

        CompletionUtil.addCompletions(result, element.project)
    }
}
