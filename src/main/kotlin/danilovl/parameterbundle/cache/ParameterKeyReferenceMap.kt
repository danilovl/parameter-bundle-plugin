package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import danilovl.parameterbundle.util.ParseUtil

class ParameterKeyReferenceMap {
    lateinit var referenceMap: HashMap<String, ParameterKeyReference>

    fun create(project: Project): ParameterKeyReferenceMap {
        val parameterKeyReferenceMap = ParameterKeyReferenceMap()
        parameterKeyReferenceMap.referenceMap = ParseUtil.getKeyReferences(project)

        return parameterKeyReferenceMap
    }
}
