package danilovl.parameterbundle.reference

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import danilovl.parameterbundle.util.ReferenceUtil
import danilovl.parameterbundle.util.TwigUtil

class TwigParameterGoToDeclaration : GotoDeclarationHandler {
    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor?): Array<PsiElement> {
        val targets = arrayOf<PsiElement>()

        if (sourceElement === null) {
            return targets
        }

        if(!getPrintBlockOrTagFunctionPattern(sourceElement)){
            return targets
        }

        return ReferenceUtil.getReferences(sourceElement)
    }

    private fun getPrintBlockOrTagFunctionPattern(psiElement: PsiElement): Boolean {
        return TwigUtil.basePlatformPattern().accepts(psiElement)
    }
}
