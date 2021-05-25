package es.wokis.services

import es.wokis.plugins.config
import io.ktor.http.content.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.naming.NoPermissionException

class ImageService {
    companion object {
        private val imageFolder = config.getString("imagefolder")
        private const val AVATARS = "avatars"
        private const val LOGOS = "logos"

        fun getAvatar(username: String): File {
            return getImage(username)
        }

        fun getLogo(empresaName: String): File {
            return getImage(empresaName)
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
            return insertImage(AVATARS, name, image)
        }

        fun insertLogo(name: String, image: PartData.FileItem): String {
            return insertImage(LOGOS, name, image)
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

                Files.copy(imageInputStream, imagePath.toPath())

                imageInputStream.close()

                return imagePath.toPath().toString()
            } catch (exc: NoPermissionException) {
                /* no-op */
            }

            return ""
        }
    }
}