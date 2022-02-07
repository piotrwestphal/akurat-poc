package com.akurat.plugins

import com.akurat.coreModule
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        // TODO: there is some issue with ktor/koin integration https://github.com/InsertKoinIO/koin/issues/1188#
        // try in some time to bump a koin version and remove an error log level below
        slf4jLogger(level = Level.ERROR)
        modules(coreModule)
    }
}