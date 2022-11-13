package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager

object ParameterIndex {
    private val DEV_DEBUG_CONTAINER_MAP: Key<CachedValue<ParameterKeyValueMap>> = Key<CachedValue<ParameterKeyValueMap>>("DEV_DEBUG_CONTAINER_MAP")
    private val KEY_REFERENCE_MAP: Key<CachedValue<ParameterKeyReferenceMap>> = Key<CachedValue<ParameterKeyReferenceMap>>("KEY_REFERENCE_MAP")

    @Synchronized
    fun getParameterMap(project: Project): ParameterKeyValueMap {
        return CachedValuesManager
            .getManager(project)
            .getCachedValue(
                project,
                DEV_DEBUG_CONTAINER_MAP,
                {
                    val translationStringMap: ParameterKeyValueMap = ParameterKeyValueMap().create(project)

                    CachedValueProvider.Result.create(
                        translationStringMap,
                        FileModificationModificationTracker(project)
                    )
                },
                false
            )
    }

    @Synchronized
    fun getParameterReferenceMap(project: Project): ParameterKeyReferenceMap {
        return CachedValuesManager
            .getManager(project)
            .getCachedValue(
                project,
                KEY_REFERENCE_MAP,
                {
                    val parameterKeyReferenceMap: ParameterKeyReferenceMap = ParameterKeyReferenceMap().create(project)

                    CachedValueProvider.Result.create(
                        parameterKeyReferenceMap,
                        FileModificationModificationTracker(project)
                    )
                },
                false
            )
    }
}
