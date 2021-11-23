package com.akurat

import com.akurat.model.Profile
import com.akurat.plugins.SomeData
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.koin.test.junit5.AutoCloseKoinTest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest: AutoCloseKoinTest() {

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
            with(handleRequest(HttpMethod.Post, "/" ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(SomeData("Piotr")))
            }) {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals("Piotr", Json.decodeFromString<Profile>(response.content!!).name)
            }
        }
    }

    @Test
    fun `should authorize with simple credentials`() {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/protected/route/basic" ) {
                val credentials = Base64.getEncoder().encodeToString("Piotr:Piotr".toByteArray())
                addHeader("Authorization", "Basic $credentials")
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello Piotr!", response.content)
            }
        }
    }
}