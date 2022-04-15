package com.akurat

import com.akurat.plugins.configureHTTP
import com.akurat.plugins.configureMonitoring
import com.akurat.plugins.configureSecurity
import com.akurat.plugins.configureSerialization
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

// java -jar sample-app.jar -config=anotherfile.conf
// TODO: https://github.com/InsertKoinIO/koin/tree/main/examples/multimodule-ktor
fun Application.appModule() {
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureErrorHandler()
    koin {
        slf4jLogger(level = Level.ERROR)
        modules(
            profileModule(getEnvironment()),
        )
    }
}

fun Application.getEnvironment(): String? = environment.config.propertyOrNull("ktor.environment")?.getString()