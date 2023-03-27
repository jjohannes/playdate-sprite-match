package org.example

import groovy.transform.CompileStatic
import org.example.tasks.GameAssetPacksProcessor
import org.example.tasks.PlaydateCompile
import org.example.tasks.PlaydateSdkInstall
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.util.PatternFilterable

@CompileStatic
abstract class PlaydatePictureGamePlugin implements Plugin<Project> {

    void apply(Project project) {
        def playdateSdk = project.configurations.create('playdateSdk')
        def gameAssets = project.configurations.create('gameAssets')


        def installPlaydateSdk = project.tasks.register('installPlaydateSdk', PlaydateSdkInstall) {
            it.sdkPkg.from(project.zipTree(playdateSdk.elements.map { it.first() }).matching { PatternFilterable p ->
                p.exclude('__MACOSX')
            })
            it.sdkLocation.set(project.providers.systemProperty('user.home').map { new File(new File(it), 'Developer/PlaydateSDK') }.get())
        }

        def processGameAssetPacks = project.tasks.register('processGameAssetPacks', GameAssetPacksProcessor) {
            it.assetPack.from(project.zipTree(gameAssets.elements.map { it.first() }).matching { PatternFilterable p ->
                p.include('**/monochrome.png')
                p.include('**/monochrome_tilemap.png')
                p.include('**/tilemap_black.png')
            })
            it.spriteSize.set(16)
            it.spriteSpacing.set(1)
            it.assetFolder.set(project.layout.buildDirectory.dir('assets'))
        }

        def mergeSourceAndAssets = project.tasks.register('mergeSourceAndAssets', Sync) {
            it.destinationDir = project.layout.buildDirectory.dir('merged').get().asFile

            it.from(project.layout.projectDirectory.dir('source'))
            it.from(processGameAssetPacks) { CopySpec c -> c.into('images') }
        }

        project.tasks.register('compilePlaydate', PlaydateCompile) {
            it.group = 'build'

            it.playdateSdk.set(installPlaydateSdk.flatMap { it.sdkLocation })
            it.gameSource.set(project.layout.dir(mergeSourceAndAssets.map { it.destinationDir }))
            it.pdx.set(project.layout.buildDirectory.dir("dist/${project.name}.pdx"))
        }

    }
}