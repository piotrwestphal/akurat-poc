package com.akurat

import java.io.IOException
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths

class StatementsExtractor {
    companion object {
        fun extract(fileName: String): List<String> {
            val uri = StatementsExtractor::class.java.classLoader.getResource(fileName)?.toURI()
                ?: throw IOException("$fileName not found")
            val path = if (uri.toString().contains("!")) {
                // This happens when the file is in a packaged JAR
                val (fs, file) = uri.toString().split("!")
                FileSystems.newFileSystem(URI.create(fs), emptyMap<String, Any>())
                    .getPath(file)
            } else {
                Paths.get(uri)
            }
            return Files.readString(path).split(";")
                .map(String::trim)
                .filter(String::isNotEmpty)
                .toList()
        }
    }
}