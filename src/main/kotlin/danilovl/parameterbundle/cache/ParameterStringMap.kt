package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import danilovl.parameterbundle.util.ParseUtil

class ParameterStringMap {
    lateinit var parameterMap: HashMap<String, String>

    fun create(project: Project): ParameterStringMap {
        val translationStringMap = ParameterStringMap()
        translationStringMap.parameterMap = ParseUtil.getKernelDevDebugContainerParameters(project)

        return translationStringMap
    }
}
