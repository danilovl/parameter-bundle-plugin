package danilovl.parameterbundle.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import danilovl.parameterbundle.util.TwigUtil

class TwigParameterCompletionContributor : CompletionContributor() {
    override fun invokeAutoPopup(position: PsiElement, typeChar: Char): Boolean {
        return typeChar == '\'' || typeChar == '"'
    }

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.or(getPrintBlockOrTagFunctionPattern()),
            TwigParameterCompletionProvider()
        )
    }

    private fun getPrintBlockOrTagFunctionPattern(): ElementPattern<PsiElement> {
        return TwigUtil.basePlatformPattern()
    }
}
