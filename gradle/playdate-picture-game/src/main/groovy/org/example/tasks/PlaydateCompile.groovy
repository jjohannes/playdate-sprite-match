package org.example.tasks

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import javax.inject.Inject

@CompileStatic
@CacheableTask
abstract class PlaydateCompile extends DefaultTask {

    @InputDirectory
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract DirectoryProperty getPlaydateSdk()

    @InputDirectory
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract DirectoryProperty getGameSource()

    @OutputDirectory
    abstract DirectoryProperty getPdx()

    @Inject
    abstract ExecOperations getExecOperations()

    @TaskAction
    void compile() {
        execOperations.exec {
            it.commandLine(playdateSdk.file('bin/pdc').get().asFile, gameSource.get().asFile, pdx.get().asFile)
        }
    }
}