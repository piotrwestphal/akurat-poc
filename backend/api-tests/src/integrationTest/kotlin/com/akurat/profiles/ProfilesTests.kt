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

    companion object {
        private val BASE_PROFILES_PATH = "http://localhost:8080/api/v1/profiles"
    }

    @Test
    fun `should create`(): Unit =
        runBlocking {
            val now = Instant.now().toEpochMilli()
            val response: HttpResponse = httpClient().post(BASE_PROFILES_PATH) {
                contentType(ContentType.Application.Json)
                body = CreateProfileRequest("West", Role.MODEL)
            }
            println("REQUEST RESPONSE: ${response.receive<String>()}")
            assertThat(response.status).isEqualTo(HttpStatusCode.Created)
            val profileResponse = response.receive<ProfileResponse>()
            assertThat(response.headers["Location"]).contains("/api/v1/profiles/${profileResponse.id}")

            // TODO: add GET call and then check response
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
            val deleteResponse: HttpResponse = httpClient().delete("$BASE_PROFILES_PATH/$id")
            println("REQUEST RESPONSE: ${deleteResponse.receive<String>()}")
            assertThat(deleteResponse.status).isEqualTo(HttpStatusCode.OK)

            val getResponse: HttpResponse = httpClient().get("$BASE_PROFILES_PATH/$id")
            assertThat(getResponse.status).isEqualTo(HttpStatusCode.NotFound)
        }

    @Test
    fun `should retrieve single`(): Unit =
        runBlocking {
            val (id, name, role, createdAt, updatedAt) = createProfile("West", Role.MODEL)
            val response: HttpResponse = httpClient().get("$BASE_PROFILES_PATH/$id")
            println("REQUEST RESPONSE: ${response.receive<String>()}")

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
            val profile1 = createProfile("King Richard", Role.DESIGNER)
            val profile2 = createProfile("Macio Moretti", Role.MODEL)
            val profile3 = createProfile("Marcin Masecki", Role.PHOTOGRAPHER)
            val profile4 = createProfile("Linda McCartney", Role.DESIGNER)
            val profile5 = createProfile("Daniel Lobotom", Role.MODEL)

            val response: HttpResponse = httpClient().get(BASE_PROFILES_PATH)
            println("REQUEST RESPONSE: ${response.receive<String>()}")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            val profilesResponse = response.receive<List<ProfileResponse>>()
            assertThat(profilesResponse.map { it.id }).containsExactlyInAnyOrder(
                profile1.id.toString(),
                profile2.id.toString(),
                profile3.id.toString(),
                profile4.id.toString(),
                profile5.id.toString(),
            )
        }

    @Test
    fun `should update`(): Unit =
        runBlocking {
            val createdProfile = createProfile("Morgan Woodpecker", Role.PHOTOGRAPHER)
            val response: HttpResponse = httpClient().patch("$BASE_PROFILES_PATH/${createdProfile.id}") {
                contentType(ContentType.Application.Json)
                body = UpdateProfileRequest("Morgan Freeman", Role.MODEL)
            }

            println("REQUEST RESPONSE: ${response.receive<String>()}")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            SoftAssertions().apply {
                val (id, name, role, createdAt, updatedAt) = response.receive<ProfileResponse>()
                assertThat(id).isEqualTo(createdProfile.id.toString())
                assertThat(name).isEqualTo("Morgan Freeman")
                assertThat(role).isEqualTo(Role.MODEL.name)
                assertThat(createdAt).isEqualTo(createdProfile.createdAt.toEpochMilli())
                assertThat(updatedAt).isGreaterThan(createdProfile.updatedAt.toEpochMilli())
            }.assertAll()
        }
}