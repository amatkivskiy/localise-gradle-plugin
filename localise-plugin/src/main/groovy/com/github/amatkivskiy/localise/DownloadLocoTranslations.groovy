package com.github.amatkivskiy.localise

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException

class DownloadLocoTranslations extends DefaultTask {
    static def separator = File.separator
    String fileName
    String locoBuildDirectory
    String exportUrl
    Project project

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    def handleLocoTranslation() {
        // Construct absolute path of file that later will be used as target source for zip stream.
        def fullZipFilePath = "$project.buildDir.path$separator$locoBuildDirectory$separator$fileName"

        project.logger.info("Downloaded Zip file path will be : $fullZipFilePath")

        // Cleanup any files/folder left from previous task run.
        createProperDirectoryAndRemoveOldTranslations()
        // Save zip stream content into fullZipFilePath file.
        saveUrlContentToFile(fullZipFilePath)

        // Unzip target zip file into project.buildDir/loco/unzipped
        def unzippedFolderPath = unzipReceivedZipFile(fullZipFilePath)

        // Find main flavor's resource directory of Android app
        def mainResDir = project.android.sourceSets.findByName("main").res.srcDirs.first()
        project.logger.info("Application 'main' configuration resource folder: $mainResDir")

        project.logger.lifecycle("Copping unzipped translation into application resources")

        // Copy unzipped res content into main flavor's resource directory replacing previous content.
        project.copy {
            from "${getUnzippedResFolderPath(unzippedFolderPath)}"
            into "$mainResDir"
        }
    }

    static def getUnzippedResFolderPath(File unzippedDir) {
        return unzippedDir.absolutePath + separator + unzippedDir.list().first() + separator + "res"
    }

    private File unzipReceivedZipFile(fullZipFilePath) {
        def unzippedDir = new File("$project.buildDir.path${separator}loco${separator}unzipped")
        unzippedDir.mkdirs()

        project.logger.lifecycle("Unzipping downloaded file into folder.")

        project.copy {
            from project.zipTree(new File(fullZipFilePath))
            into unzippedDir
        }

        unzippedDir
    }

    private void saveUrlContentToFile(String zipFilePath) {
        try {
            new File(zipFilePath).withOutputStream { out ->
                out << new URL(exportUrl).openStream()
            }
        } catch (Exception exception) {
            project.logger.error("Failed to download translations zip file", exception)
            throw new TaskExecutionException(this, exception)
        }
    }

    private void createProperDirectoryAndRemoveOldTranslations() {
        // Create translation directory in the project build directory
        def locoBuildDir = new File("$project.buildDir.path$separator$locoBuildDirectory")

        project.logger.lifecycle("Deleting translations output build directory: $locoBuildDir")

        locoBuildDir.deleteDir()
        locoBuildDir.mkdirs()
    }
}