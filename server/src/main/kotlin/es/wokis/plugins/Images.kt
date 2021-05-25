package es.wokis.plugins

import io.ktor.application.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun Application.initImages() {
    val imagePath = config.getString("imagefolder")
    val imageFolder = File(imagePath + File.separator, "default.png").normalize()

    val defaultImagePath = this::class.java.getResource("/default.png") ?: throw IllegalAccessException()
    val defaultImage = Paths.get(File(defaultImagePath.toURI()).path)

    if (!imageFolder.exists()) {
        imageFolder.mkdirs()

        File("$imagePath/logos").mkdirs()
        File("$imagePath/avatars").mkdirs()

        Files.copy(defaultImage, imageFolder.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

}