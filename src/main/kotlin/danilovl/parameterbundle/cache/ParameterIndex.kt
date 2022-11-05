package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager

object ParameterIndex {
    private val DEV_DEBUG_CONTAINER_MAP_COMPILED: Key<CachedValue<ParameterStringMap>> = Key<CachedValue<ParameterStringMap>>("DEV_DEBUG_CONTAINER_MAP_COMPILED")

    @Synchronized
    fun getParameterMap(project: Project): ParameterStringMap {
        return CachedValuesManager
            .getManager(project)
            .getCachedValue(
                project,
                DEV_DEBUG_CONTAINER_MAP_COMPILED,
                {
                    val translationStringMap: ParameterStringMap = ParameterStringMap().create(project)

                    CachedValueProvider.Result.create(
                        translationStringMap,
                        FileModificationModificationTracker(project)
                    )
                },
                false
            )
    }
}
