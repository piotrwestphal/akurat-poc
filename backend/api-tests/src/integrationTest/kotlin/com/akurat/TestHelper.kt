package com.akurat

import com.akurat.model.Profile
import com.akurat.model.Role
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import java.time.Instant
import java.util.*

fun httpClient() = HttpClient(CIO) {
    expectSuccess = false
    install(Logging)
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

fun TestServer.Companion.createProfile(profileName: String, profileRole: Role): Profile {
    val profile = Profile(
        id = UUID.randomUUID(),
        name = profileName,
        role = profileRole,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )
    val (id, name, role, createdAt, updatedAt) = profile
    val cqlQuery = insertInto("profiles")
        .value("profile_id", literal(id))
        .value("name", literal(name))
        .value("role", literal(role.name))
        .value("created_at", literal(createdAt))
        .value("updated_at", literal(updatedAt))
        .asCql()
    dbHelper.executeScript(cqlQuery, "kingboo")
    return profile
}
