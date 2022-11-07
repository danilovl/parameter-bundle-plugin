package danilovl.parameterbundle.cache

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SimpleModificationTracker
import danilovl.parameterbundle.Setting
import java.io.File

class FileModificationModificationTracker(private val project: Project) : SimpleModificationTracker() {
    private var lastModified: Long = 0

    override fun getModificationCount(): Long {
        val filePath = this.project.basePath!! + Setting.DEV_DEBUG_CONTAINER_XML_PATH
        val modified: Long = File(filePath).lastModified()

        if (modified != this.lastModified) {
            this.lastModified = modified
            incModificationCount()
        }

        return super.getModificationCount()
    }
}
