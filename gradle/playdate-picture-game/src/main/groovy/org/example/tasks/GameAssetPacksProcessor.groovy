package org.example.tasks

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.internal.file.FileOperations
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

@CompileStatic
abstract class GameAssetPacksProcessor extends DefaultTask {

    @InputFiles
    abstract ConfigurableFileCollection getAssetPack()

    @Input
    abstract Property<Integer> getSpriteSize()

    @Input
    abstract Property<Integer> getSpriteSpacing()

    @OutputDirectory
    abstract DirectoryProperty getAssetFolder()
    
    @Inject
    abstract FileOperations getFileOperations()

    @TaskAction
    void process() {
        fileOperations.delete(assetFolder.get().asFile.listFiles())

        fixImageTable()
    }

    private void fixImageTable() {
        def totalSize = spriteSize.get() + spriteSpacing.get()
        def image = ImageIO.read(assetPack.singleFile)
        def columns = (image.width / totalSize) as int
        def rows = (image.height / totalSize) as int

        def imageWithoutSpacing = new BufferedImage(columns * spriteSize.get(), rows * spriteSize.get(), image.type)
        def graphics = imageWithoutSpacing.createGraphics()
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, imageWithoutSpacing.width, imageWithoutSpacing.height) // replace transparency with white

        for (y in 0..<rows) {
            for (x in 0..<columns) {
                def toX = spriteSize.get() * x
                def toY = spriteSize.get() * y
                def toX2 = toX + spriteSize.get()
                def toY2 = toY + spriteSize.get()

                def fromX = totalSize * x
                def fromY = totalSize * y
                def fromX2 = fromX + spriteSize.get()
                def fromY2 = fromY + spriteSize.get()

                graphics.drawImage(image,
                    toX, toY, toX2, toY2,
                    fromX, fromY, fromX2, fromY2, null
                )
            }
        }

        ImageIO.write(imageWithoutSpacing, 'png',
            assetFolder.file("pictures-table-${spriteSize.get()}-${spriteSize.get()}.png").get().asFile)
    }
}