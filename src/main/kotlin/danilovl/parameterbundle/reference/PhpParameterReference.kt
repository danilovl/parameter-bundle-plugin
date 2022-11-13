package danilovl.parameterbundle.reference

import com.intellij.psi.*
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import danilovl.parameterbundle.util.ReferenceUtil
import danilovl.parameterbundle.util.StringUtil

class PhpParameterReference(element: StringLiteralExpression, private val psiElement: PsiElement) : PsiPolyVariantReferenceBase<PsiElement?>(element) {
    private val parameterName: String

    init {
        parameterName = element.contents
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val results: MutableList<ResolveResult> = ArrayList()
        val parameterName = StringUtil.removeQuotationMark(psiElement.text)

        ReferenceUtil.addReferencesToResolveResult(results, psiElement.project, parameterName)

        return results.toTypedArray()
    }
}
