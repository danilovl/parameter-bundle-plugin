package danilovl.parameterbundle.reference

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import danilovl.parameterbundle.Setting
import danilovl.parameterbundle.util.PhpUtil

class PhpParameterReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(psiReferenceRegistrar: PsiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(
            PlatformPatterns.psiElement(StringLiteralExpression::class.java),

            object : PsiReferenceProvider() {
                override fun getReferencesByElement(psiElement: PsiElement, processingContext: ProcessingContext): Array<PsiReference> {
                    val method = PhpUtil.resolveMethodReference(psiElement)
                    if (method === null) {
                        return PsiReference.EMPTY_ARRAY
                    }

                    val isInstanceOf = PhpUtil.isMethodReferenceInstanceOf(method, Setting.PARAMETER_SERVICE_INTERFACE_NAMESPACE)
                    if (!isInstanceOf) {
                        return PsiReference.EMPTY_ARRAY
                    }

                    return arrayOf(PhpParameterReference(psiElement as StringLiteralExpression, psiElement))
                }
            }
        )
    }
}
