package es.wokis.plugins

import io.ktor.application.*
import java.io.File

fun Application.initImages() {
    val imagePath = config.getString("imagepath")
    val imageFolder = File(imagePath)

    if (!imageFolder.exists()) {
        imageFolder.mkdirs()

        File("$imagePath/logos").mkdirs()
        File("$imagePath/avatars").mkdirs()
    }
}