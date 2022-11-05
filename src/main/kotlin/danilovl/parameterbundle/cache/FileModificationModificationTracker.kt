package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SimpleModificationTracker
import danilovl.parameterbundle.Setting
import java.io.File

class FileModificationModificationTracker(private val project: Project) : SimpleModificationTracker() {
    private var lastHash: Long = 0

    override fun getModificationCount(): Long {
        val filePath = this.project.basePath!! + Setting.DEFAULT_DEV_DEBUG_CONTAINER_PATH
        val hash: Long = File(filePath).lastModified()

        if (hash != this.lastHash) {
            this.lastHash = hash
            incModificationCount()
        }

        return super.getModificationCount()
    }
}
