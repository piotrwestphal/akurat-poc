package com.akurat

import com.akurat.model.Profile
import com.akurat.plugins.SomeData
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.test.junit5.AutoCloseKoinTest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ApplicationTest : AutoCloseKoinTest() {

    @Test
    fun `should get 'Hello World!' in response`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[\n]", response.content)
            }
        }
    }

    @Test
    fun `should create message for given user`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Post, "/") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(SomeData("West")))
            }) {
                val profile = Json.decodeFromString<Profile>(response.content!!)
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals("West", profile.name)
            }
        }
    }

    @Test
    fun `should authorize with simple credentials`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/protected/route/basic") {
                val credentials = Base64.getEncoder().encodeToString("West:West".toByteArray())
                addHeader("Authorization", "Basic $credentials")
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello West!", response.content)
            }
        }
    }

    @Test
    fun `should get error message when there is no profile with given name`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/West")) {
                val errorMessage = Json.decodeFromString<ErrorMessage>(response.content!!)
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("Not Found", errorMessage.error)
                assertEquals("Profile with name 'West' not found", errorMessage.message)
            }
        }
    }

    @Test
    fun `should get error message when there is bad parameter in request body`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Post, "/") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("{\"test\": \"West\"}")
            }){
                val errorMessage = Json.decodeFromString<ErrorMessage>(response.content!!)
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals("Bad Request", errorMessage.error)
                assertContains(errorMessage.message!!, "Field 'text' is required for type")
            }
        }
    }
}