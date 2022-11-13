package danilovl.parameterbundle.util

import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.jetbrains.twig.TwigLanguage
import com.jetbrains.twig.TwigTokenTypes
import com.jetbrains.twig.elements.TwigElementTypes
import danilovl.parameterbundle.Setting

class TwigUtil {
    companion object {
        fun basePlatformPattern(): ElementPattern<PsiElement> {
            return PlatformPatterns
                .psiElement(TwigTokenTypes.STRING_TEXT)
                .withParent(
                    PlatformPatterns.or(
                        PlatformPatterns.psiElement(TwigElementTypes.FUNCTION_CALL)
                    )
                )
                .afterLeafSkipping(
                    PlatformPatterns.or(
                        PlatformPatterns.psiElement(TwigTokenTypes.LBRACE),
                        PlatformPatterns.psiElement(PsiWhiteSpace::class.java),
                        PlatformPatterns.psiElement(TwigTokenTypes.WHITE_SPACE),
                        PlatformPatterns.psiElement(TwigTokenTypes.SINGLE_QUOTE),
                        PlatformPatterns.psiElement(TwigTokenTypes.DOUBLE_QUOTE)
                    ),
                    PlatformPatterns
                        .psiElement(TwigTokenTypes.IDENTIFIER)
                        .withText(PlatformPatterns.string().oneOf(*Setting.TWIG_FUNCTION_NAMES))
                )
                .withLanguage(TwigLanguage.INSTANCE)
        }
    }
}
