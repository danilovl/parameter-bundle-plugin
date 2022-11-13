package danilovl.parameterbundle.util

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.ResolveResult
import danilovl.parameterbundle.cache.ParameterIndex

class ReferenceUtil {
    companion object {
        fun addReferencesToResolveResult(results: MutableList<ResolveResult>, project: Project, parameterName: String) {
            val parameterKeyReference = ParameterIndex.getParameterReferenceMap(project).referenceMap.get(parameterName)
            if (parameterKeyReference === null) {
                return
            }

            if (parameterKeyReference.yaml !== null) {
                results.add(PsiElementResolveResult(parameterKeyReference.yaml as PsiElement))
            }

            if (parameterKeyReference.xml !== null) {
                results.add(PsiElementResolveResult(parameterKeyReference.xml as PsiElement))
            }
        }

        fun getReferences(psiElement: PsiElement): Array<PsiElement> {
            val references = mutableListOf<PsiElement>()
            val parameterName = StringUtil.removeQuotationMark(psiElement.text)

            val parameterKeyReference =
                ParameterIndex.getParameterReferenceMap(psiElement.project).referenceMap.get(parameterName)
            if (parameterKeyReference === null) {
                return references.toTypedArray()
            }
            if (parameterKeyReference.yaml !== null) {
                references.add(parameterKeyReference.yaml as PsiElement)
            }

            if (parameterKeyReference.xml !== null) {
                references.add(parameterKeyReference.xml as PsiElement)
            }

            return references.toTypedArray()
        }
    }
}
