package es.wokis.utils

import java.util.*
import kotlin.streams.asSequence

class HashGenerator {
    companion object {
        /**
         * Genera un hash aleatorio con la longitud indicada.
         *
         * @param length la longitud del hash. Es opcional, tiene una de serie de 12 caracteres.
         * @return el hash generado como [String]
         *
         */
        fun generateHash(length: Long = 12L): String {
            val hashSource = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_"
            return Random().ints(length, 0, hashSource.length-1)
                .asSequence()
                .map(hashSource::get)
                .joinToString("")
        }
    }
}