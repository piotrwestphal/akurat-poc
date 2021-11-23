package com.akurat.plugins

import com.akurat.AppService
import com.akurat.ProfilesAccessor
import com.akurat.ProfilesService
import io.ktor.application.*
import org.koin.core.scope.get
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

val appModule = module(createdAtStart = true) {
    single{AppService(get())}
    single<ProfilesService>{ProfilesAccessor()}
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}