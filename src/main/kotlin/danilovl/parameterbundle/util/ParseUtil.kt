package danilovl.parameterbundle.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.xml.XmlFile
import com.intellij.util.xml.DomManager
import danilovl.parameterbundle.Setting
import danilovl.parameterbundle.cache.ParameterKeyReference
import danilovl.parameterbundle.meta.ParameterDomElement
import danilovl.parameterbundle.meta.RootDomElement
import java.io.File

class ParseUtil {
    companion object {
        fun getKernelDevDebugContainerParameters(project: Project): HashMap<String, String> {
            setParametersKeyDelimiter(project)

            var parameters = HashMap<String, String>()

            val containerParameters = getContainerParameters(project)
            if (containerParameters === null) {
                return parameters
            }

            containerParameters.forEach {
                parameters = parameters.plus(XmlUtil.parseXmlTag(it!!.xmlTag!!)) as HashMap<String, String>
            }

            return parameters
        }

        fun getKeyReferences(project: Project): HashMap<String, ParameterKeyReference> {
            setParametersKeyDelimiter(project)

            val keyReference = HashMap<String, ParameterKeyReference>()

            val containerParameters = getContainerParameters(project)
            if (containerParameters === null) {
                return keyReference
            }

            var keyXmlTagReference = HashMap<String, PsiElement>()

            containerParameters.forEach {
                keyXmlTagReference = keyXmlTagReference.plus(XmlUtil.parseXmlTagWithOnlyValue(it!!.xmlTag!!)) as HashMap<String, PsiElement>
            }

            keyXmlTagReference.forEach {
                keyReference[it.key] = ParameterKeyReference(xml = it.value)
            }

            YamlUtil.parseConfigYaml(keyReference, project)

            return keyReference
        }

        private fun setParametersKeyDelimiter(project: Project) {
            val containerParameters = getContainerParameters(project)
            if (containerParameters === null) {
                Setting.setDelimiter(Setting.DEFAULT_PARAMETERS_KEY_DELIMITER)

                return
            }

            for (parameterDomElement: ParameterDomElement? in containerParameters) {
                val key = parameterDomElement!!.xmlTag!!.getAttribute("key")?.value
                if (key == Setting.BUNDLE_CONFIGURATION_KEY) {
                    val value = parameterDomElement.xmlTag!!.value.text
                    Setting.setDelimiter(value)

                    return
                }
            }

            Setting.setDelimiter(Setting.DEFAULT_PARAMETERS_KEY_DELIMITER)
        }

        private fun getContainerParameters(project: Project): List<ParameterDomElement?>? {
            val filePath = project.basePath!! + Setting.DEV_DEBUG_CONTAINER_XML_PATH
            val kernelDevDebugContainer = File(filePath)

            val virtualKernelDevDebugContainerFile = VfsUtil.findFileByIoFile(kernelDevDebugContainer, false)
            if (virtualKernelDevDebugContainerFile == null || !virtualKernelDevDebugContainerFile.exists()) {
                return null
            }

            val psiFile: PsiFile = PsiManager.getInstance(project).findFile(virtualKernelDevDebugContainerFile)!!
            if (psiFile !is XmlFile) {
                return null
            }

            val domManager = DomManager.getDomManager(project)
            val fileElement = domManager.getFileElement(psiFile, RootDomElement::class.java) ?: return null

            val root: RootDomElement = fileElement.getRootElement()

            return root.getParameters()?.getParameters()
        }
    }
}
