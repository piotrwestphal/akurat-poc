package com.akurat

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

// https://github.com/DataStax-Examples/object-mapper-jvm/tree/master/kotlin
// https://github.com/DataStax-Examples/getting-started-with-astra-java
// https://ktor.io/docs/testing.html#end-to-end
// TODO: remove!!
class Connector {

    fun healthCheck() {
        connect().use { session ->
            maybeCreateSchema(session)
            val rs: ResultSet = session.execute("select release_version from system.local")
            val row: Row? = rs.one()
            //Print the results of the CQL query to the console:
//            val dao = ProfileMapper.builder(session).build().dao()
            val id = UUID.randomUUID()
//            val created = dao.create(ProfileEntity(id, "piotr", "DUPA", Instant.now()))
//            val profile = dao.get(id)
//            val profiles = dao.getAll().all()
//            println(profiles.toString())
            if (row != null) {
                println(row.getString("release_version"))
            } else {
                println("An error occurred.")
            }
        }
    }

    private fun connect() = CqlSession.builder()
        .build()

    @Throws(Exception::class)
    private fun maybeCreateSchema(session: CqlSession) {
        for (statement in getStatements("create_schema.cql")) {
            session.execute(SimpleStatement.newInstance(statement))
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
}