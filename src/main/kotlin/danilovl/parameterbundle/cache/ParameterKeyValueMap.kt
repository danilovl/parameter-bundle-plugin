package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import danilovl.parameterbundle.util.ParseUtil

class ParameterKeyValueMap {
    lateinit var parameterMap: HashMap<String, String>

    fun create(project: Project): ParameterKeyValueMap {
        val parameterKeyValueMap = ParameterKeyValueMap()
        parameterKeyValueMap.parameterMap = ParseUtil.getKernelDevDebugContainerParameters(project)

        return parameterKeyValueMap
    }
}
