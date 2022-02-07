package com.akurat

import io.ktor.http.*
import io.ktor.server.testing.*
import org.assertj.core.api.Assertions.assertThat
import java.util.*
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun `should get empty array in response`() {
        testApp {
            handleRequest(HttpMethod.Get, "/profiles").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isEqualToIgnoringWhitespace("[]")
            }
        }
    }

    @Test
    fun `should create profile for given user`() {
        testApp {
            with(handleRequest(HttpMethod.Post, "/profiles") {
                val createProfileRequest = readFile("/json/createProfileRequest.json")
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(createProfileRequest)
            }) {
                val createProfileResponse = readFile("/json/createProfileResponse.json")
                assertThat(response.status()).isEqualTo(HttpStatusCode.Created)
                assertThat(response.content).isEqualToIgnoringWhitespace(createProfileResponse)
            }
        }
    }

    @Test
    fun `should authorize with simple credentials`() {
        testApp {
            with(handleRequest(HttpMethod.Get, "/protected/route/basic") {
                val credentials = Base64.getEncoder().encodeToString("West:West".toByteArray())
                addHeader("Authorization", "Basic $credentials")
            }) {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isEqualTo("Hello West!")
            }
        }
    }

    @Test
    fun `should get error message when there is no profile with given name`() {
        testApp {
            with(handleRequest(HttpMethod.Get, "/profiles/West")) {
                val expectedErrorMessage = readFile("/json/notFoundErrorMessage.json")
                assertThat(response.status()).isEqualTo(HttpStatusCode.NotFound)
                assertThat(response.content).isEqualToIgnoringWhitespace(expectedErrorMessage)
            }
        }
    }

    @Test
    fun `should get error message when there is bad parameter in request body`() {
        testApp {
            with(handleRequest(HttpMethod.Post, "/profiles") {
                val erroneousCreateProfileRequest = readFile("/json/erroneousCreateProfileRequest.json")
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(erroneousCreateProfileRequest)
            }) {
                val expectedErrorMessage = readFile("/json/badRequestErrorMessage.json")
                assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
                assertThat(response.content).isEqualToIgnoringWhitespace(expectedErrorMessage)
            }
        }
    }
}