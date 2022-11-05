package danilovl.parameterbundle.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlFile
import com.intellij.util.xml.DomManager
import danilovl.parameterbundle.Setting
import danilovl.parameterbundle.meta.ParameterDomElement
import danilovl.parameterbundle.meta.RootDomElement
import java.io.File
import java.util.HashMap

class ParseUtil {
    companion object {
        fun getKernelDevDebugContainerParameters(project: Project): HashMap<String, String> {
            var parameters = HashMap<String, String>()

            val filePath = project.basePath!! + Setting.DEFAULT_DEV_DEBUG_CONTAINER_PATH
            val kernelDevDebugContainer = File(filePath)

            val virtualKernelDevDebugContainerFile = VfsUtil.findFileByIoFile(kernelDevDebugContainer, false)
            if (virtualKernelDevDebugContainerFile == null || !virtualKernelDevDebugContainerFile.exists()) {
                return parameters
            }

            val psiFile: PsiFile = PsiManager.getInstance(project).findFile(virtualKernelDevDebugContainerFile)!!
            if (psiFile !is XmlFile) {
                return parameters
            }

            val domManager = DomManager.getDomManager(project)
            val root: RootDomElement = domManager.getFileElement(psiFile, RootDomElement::class.java)!!.getRootElement()
            val containerParameters: List<ParameterDomElement?>? = root.getParameters()!!.getParameters()

            containerParameters?.forEach {
                parameters = parameters.plus(XmlUtil.parseXmlTag(it!!.xmlTag!!)) as HashMap<String, String>
            }

            return parameters
        }
    }
}
