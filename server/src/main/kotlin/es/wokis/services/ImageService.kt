package es.wokis.services

import es.wokis.plugins.config
import io.ktor.http.content.*
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import javax.naming.NoPermissionException

class ImageService {
    companion object {
        private val imageFolder = config.getString("imagefolder")
        private const val AVATARS = "avatars"
        private const val LOGOS = "logos"

        fun getAvatar(username: String): File {
            return getImage(AVATARS, username)
        }

        fun getLogo(empresaName: String): File {
            return getImage(LOGOS, empresaName)
        }

        private fun getImage(root: String, name: String): File {
            val defaultIcon = File("$imageFolder/default.png")
            val pathString = "$imageFolder/$root/$name"
            val path = Paths.get(pathString).normalize()

            val file = File(path.toUri())

            if (file.exists()) {
                val image = file.listFiles()?.firstOrNull()
                return image ?: defaultIcon
            }

            return defaultIcon
        }

        fun insertAvatar(name: String, image: PartData.FileItem) {
            insertImage(AVATARS, name, image)
        }

        fun insertLogo(name: String, image: PartData.FileItem) {
            insertImage(LOGOS, name, image)
        }

        private fun insertImage(root: String, name: String, image: PartData.FileItem): Boolean {
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

                return true
            } catch (exc: NoPermissionException) {
                /* no-op */
            }

            return false
        }
    }
}