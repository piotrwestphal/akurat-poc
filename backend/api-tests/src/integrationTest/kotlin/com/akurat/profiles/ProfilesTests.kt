package com.akurat.profiles

import com.akurat.TestServer
import com.akurat.createProfile
import com.akurat.httpClient
import com.akurat.model.Role
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import java.time.Instant
import kotlin.test.Test

internal class ProfilesTests : TestServer() {

    @Test
    fun `should create`(): Unit =
        runBlocking {
            val now = Instant.now().toEpochMilli()
            val response: HttpResponse = httpClient().post("http://localhost:8080/api/v1/profiles") {
                contentType(ContentType.Application.Json)
                body = ProfileRequest("West", Role.MODEL)
            }

            assertThat(response.status).isEqualTo(HttpStatusCode.Created)
            SoftAssertions().apply {
                val (id, name, role, createdAt, updatedAt) = response.receive<ProfileResponse>()
                assertThat(id).isNotNull
                assertThat(response.headers["Location"]).contains("/api/v1/profiles/${id}")
                assertThat(name).isEqualTo(name)
                assertThat(role).isEqualTo(Role.MODEL.name)
                assertThat(createdAt).isGreaterThan(now)
                assertThat(updatedAt).isEqualTo(createdAt)
            }.assertAll()
        }

    @Test
    fun `should delete`(): Unit =
        runBlocking {
            val (id) = createProfile("West", Role.MODEL)
            val deleteResponse: HttpResponse = httpClient().delete("http://localhost:8080/api/v1/profiles/$id")
            assertThat(deleteResponse.status).isEqualTo(HttpStatusCode.OK)

            val getResponse: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles/$id")
            assertThat(getResponse.status).isEqualTo(HttpStatusCode.NotFound)
        }

    @Test
    fun `should retrieve single`(): Unit =
        runBlocking {
            val (id, name, role, createdAt, updatedAt) = createProfile("West", Role.MODEL)
            val response: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles/$id")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)

            SoftAssertions().apply {
                val profileResponse = response.receive<ProfileResponse>()
                assertThat(profileResponse.id).isEqualTo(id.toString())
                assertThat(profileResponse.name).isEqualTo(name)
                assertThat(profileResponse.role).isEqualTo(role.name)
                assertThat(profileResponse.createdAt).isEqualTo(createdAt.toEpochMilli())
                assertThat(profileResponse.updatedAt).isEqualTo(updatedAt.toEpochMilli())
            }.assertAll()
        }

    @Test
    fun `should retrieve all`(): Unit =
        runBlocking {
            val profiles = listOf(
                "King Richard" to Role.DESIGNER,
                "Macio Moretti" to Role.MODEL,
                "Marcin Masecki" to Role.PHOTOGRAPHER,
                "Linda McCartney" to Role.DESIGNER,
                "Daniel Lobotom" to Role.MODEL
            )

            val profileIds = profiles.map { createProfile(it.first, it.second).id }
            val response: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            val profilesResponse = response.receive<List<ProfileResponse>>()
            assertThat(profilesResponse.map { it.id }).isEqualTo(profileIds)
        }
}