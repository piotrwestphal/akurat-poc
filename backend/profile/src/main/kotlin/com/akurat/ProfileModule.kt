package com.akurat

import mu.KotlinLogging.logger
import org.koin.dsl.module

fun profileModule(env: String? = null): List<org.koin.core.module.Module> {
    logger {}.info { "Loading profile module based on env value: [$env]" }
    return when (env) {
        "ci", "prod" -> {
            profilesCassandraModule
        }
        else -> {
            profilesInMemoryModule
        }
    }
}


val profilesInMemoryModule = listOf(
    module {
        single<ProfilesService> { ProfilesAccessor(get()) }
        single<ProfilesRepository> { InMemoryProfilesRepository() }
    }
)

val profilesCassandraModule = listOf(
    module {
        single<ProfilesService> { ProfilesAccessor(get()) }
        single { ProfileDaoFactory(get()).dao() }
        single<ProfilesRepository> { CassandraProfilesRepository(get()) }
    },
    cassandraModule
)
