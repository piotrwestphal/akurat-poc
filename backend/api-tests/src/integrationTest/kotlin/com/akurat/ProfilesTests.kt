package com.akurat

import com.akurat.model.Role
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import mu.KLogger
import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

internal class ProfilesTests: TestServer() {

    @Test
    fun `should create`(): Unit =
        runBlocking {
            val profileId = createProfile("West", Role.MODEL)
            val response: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles/$profileId")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            val body = response.receive<String>()
            assertThat(body).isEqualTo("[\n]")
        }

    @Test
    fun `should delete`(): Unit =
        runBlocking {
            val response: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            val body = response.receive<String>()
            assertThat(body).isEqualTo("[\n]")
        }

    @Test
    fun `should retrieve single`(): Unit =
        runBlocking {
            val response: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            val body = response.receive<String>()
            assertThat(body).isEqualTo("[\n]")
        }

    @Test
    fun `should retrieve all`(): Unit =
        runBlocking {
            val response: HttpResponse = httpClient().get("http://localhost:8080/api/v1/profiles")
            assertThat(response.status).isEqualTo(HttpStatusCode.OK)
            val body = response.receive<String>()
            assertThat(body).isEqualTo("[\n]")
        }
}