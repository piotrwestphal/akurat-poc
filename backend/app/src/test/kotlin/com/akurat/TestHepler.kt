package com.akurat

import io.ktor.server.testing.*
import java.util.*

fun <T> testApp(testEngine: TestApplicationEngine.() -> T): T =
    withTestApplication(
        {
            appModule()
            apiModule()
        },
        testEngine
    )

fun readFile(path: String): String =
    Objects.requireNonNull(object {}.javaClass.getResource(path)).readText()