package org.example.tasks

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

@CompileStatic
abstract class PlaydateSdkInstall extends DefaultTask {

    @InputFiles
    abstract ConfigurableFileCollection getSdkPkg()

    @OutputDirectory
    abstract DirectoryProperty getSdkLocation()

    @Inject
    abstract ExecOperations getExecOperations()

    @TaskAction
    void runInstall() {
        logger.lifecycle("Installing: $sdkPkg.singleFile")
        execOperations.exec {
            it.commandLine('sudo', 'installer', '-pkg', sdkPkg.singleFile, '-target', '/')
        }
    }
}