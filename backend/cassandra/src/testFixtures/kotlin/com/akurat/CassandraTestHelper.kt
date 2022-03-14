package com.akurat

import com.datastax.oss.driver.api.core.CqlSession
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class CassandraTestHelper {
    fun executeScripts(fileName: String) {
        getSession().use { session ->
            getStatements(fileName).forEach {
                println("Executing cql statement: $it")
                session.execute(it)
            }
        }
    }

    fun executeScript(cqlQuery: String, keyspace: String? = null) {
        getSession(keyspace).use {
            println("Executing cql statement: $cqlQuery")
            it.execute(cqlQuery)
        }
    }

    @Throws(Exception::class)
    private fun getStatements(fileName: String): List<String> {
        val uri = Objects.requireNonNull(javaClass.classLoader.getResource(fileName)).toURI()
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

    private fun getSession(keyspace: String? = null) = CqlSession.builder()
        .withKeyspace(keyspace)
        .build()
}