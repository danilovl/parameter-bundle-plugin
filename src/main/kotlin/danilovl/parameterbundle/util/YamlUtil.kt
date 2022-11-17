package danilovl.parameterbundle.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import danilovl.parameterbundle.Setting
import danilovl.parameterbundle.cache.ParameterKeyReference
import org.jetbrains.yaml.YAMLUtil
import org.jetbrains.yaml.psi.YAMLFile
import java.io.File

class YamlUtil {
    companion object {
        fun parseConfigYaml(keyReference: HashMap<String, ParameterKeyReference>, project: Project) {
            File(project.basePath!! + Setting.CONFIG_DIR)
                .walk()
                .filter { file -> file.extension.equals(Setting.YAML_EXTENSION) }
                .forEach { file ->
                    val virtualKernelDevDebugContainerFile = VfsUtil.findFileByIoFile(file, false)
                    if (virtualKernelDevDebugContainerFile != null) {
                        val psiFile: PsiFile? = PsiManager.getInstance(project).findFile(virtualKernelDevDebugContainerFile)

                        if (psiFile is YAMLFile) {
                            keyReference.forEach { keyReferenceValue ->
                                val keys = Setting.PARAMETERS_KEY + Setting.PARAMETERS_KEY_DELIMITER + keyReferenceValue.key

                                val keyValue = YAMLUtil.getQualifiedKeyInFile(psiFile, keys.split(Setting.PARAMETERS_KEY_DELIMITER))
                                if (keyValue !== null) {
                                    keyReference[keyReferenceValue.key]!!.yaml = keyValue
                                }
                            }
                        }
                    }
                }
        }
    }
}
