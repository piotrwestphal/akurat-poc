package com.akurat

import io.ktor.application.*
import com.akurat.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.appModule() {
    configureKoin()
}
