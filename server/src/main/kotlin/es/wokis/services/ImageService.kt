package es.wokis.services

import es.wokis.plugins.config
import io.ktor.http.content.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.naming.NoPermissionException

class ImageService {
    private val imageFolder = config.getString("imagefolder")
    private val avatars = "avatars"
    private val logos = "logos"

    fun getAvatar(avatarPath: String): File {
        return getImage(avatarPath)
    }

    fun getLogo(logoPath: String): File {
        return getImage(logoPath)
    }

    private fun getImage(path: String): File {
        val defaultIcon = File("$imageFolder/default.png")
        val imagePath = Paths.get(path).normalize()

        val file = File(imagePath.toUri())

        return if (file.exists()) {
            file
        } else {
            defaultIcon
        }

    }

    fun insertAvatar(name: String, image: PartData.FileItem): String {
        return insertImage(avatars, name, image)
    }

    fun insertLogo(name: String, image: PartData.FileItem): String {
        return insertImage(logos, name, image)
    }

    private fun insertImage(root: String, name: String, image: PartData.FileItem): String {
        try {
            val imageExtension = image.contentType?.contentSubtype
            val imageName = "$name.${imageExtension}"
            val imagePath = File("$imageFolder/$root/$name", imageName).normalize()

            val imageInputStream = image.streamProvider.invoke()

            if (!imagePath.exists()) {
                imagePath.mkdirs()
            }

            Files.copy(imageInputStream, imagePath.toPath(), StandardCopyOption.REPLACE_EXISTING)

            imageInputStream.close()

            return imagePath.toPath().toString()
        } catch (exc: NoPermissionException) {
            /* no-op */
        }

        return ""
    }
}