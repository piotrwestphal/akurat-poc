package com.akurat

import com.akurat.model.Role
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import java.time.Instant
import java.util.*

fun httpClient() = HttpClient(CIO) {
    install(Logging)
}

fun TestServer.Companion.createProfile(name: String, role: Role): String {
    val id = UUID.randomUUID()
    val cqlQuery = insertInto("profiles")
        .value("profile_id", literal(id))
        .value("name", literal(name))
        .value("role", literal(role.name))
        .value("created_at", literal(Instant.now()))
        .asCql()
    dbHelper.executeScript(cqlQuery, "kingboo")
    return id.toString()
}
