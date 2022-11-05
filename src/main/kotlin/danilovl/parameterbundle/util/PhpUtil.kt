package danilovl.parameterbundle.util

import com.intellij.openapi.util.Ref
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import org.apache.commons.lang.StringUtils
import java.util.stream.Collectors

class PhpUtil {
    companion object {
        fun isMethodReferenceInstanceOf(methodReference: MethodReference, expectedClassName: String): Boolean {
            val instance = PhpIndex.getInstance(methodReference.project)
            val classType = PhpType().add(methodReference.classReference).global(methodReference.project)

            val instanceClasses: Collection<PhpClass> = classType.types.stream().flatMap { fqn: String? ->
                instance.getAnyByFQN(fqn).stream()
            }.distinct().collect(Collectors.toList())

            for (phpClass in instanceClasses) {
                val method = phpClass.findMethodByName(methodReference.name) ?: continue
                val containingClass = method.containingClass ?: continue

                if (!isInstanceOf(containingClass, expectedClassName)) {
                    continue
                }

                return true
            }

            return false
        }

        private fun isInstanceOf(subjectClass: PhpClass, expectedClassAsString: String): Boolean {
            if ("\\" + StringUtils.stripStart(expectedClassAsString, "\\") == subjectClass.fqn) {
                return true
            }

            for (expectedClass in PhpIndex.getInstance(subjectClass.project).getAnyByFQN(expectedClassAsString)) {
                if (isInstanceOf(subjectClass, expectedClass)) {
                    return true
                }
            }

            return false
        }

        private fun isInstanceOf(subjectClass: PhpClass, expectedClass: PhpClass): Boolean {
            val result = Ref(false)

            PhpClassHierarchyUtils.processSupers(subjectClass, true, true) { superClass: PhpClass ->
                if (StringUtil.equalsIgnoreCase(superClass.fqn, expectedClass.fqn)) {
                    result.set(true)
                }

                val equalsIgnoreCase = StringUtil.equalsIgnoreCase(
                    StringUtils.stripStart(superClass.fqn, "\\"),
                    StringUtils.stripStart(expectedClass.fqn, "\\")
                )

                if (equalsIgnoreCase) {
                    result.set(true)
                }

                (!result.get())
            }

            if (result.get()) {
                return true
            }

            return PhpType()
                .add(expectedClass)
                .isConvertibleFrom(
                    PhpType().add(subjectClass),
                    PhpIndex.getInstance(subjectClass.project)
                )
        }

        fun resolveMethodReference(element: PsiElement?, depthLimit: Int = 10): MethodReference? {
            if (element == null || depthLimit <= 0) {
                return null
            }

            if (element.parent is MethodReference) {
                return element.parent as MethodReference
            }

            return resolveMethodReference(element.parent, depthLimit - 1)
        }
    }
}
