package com.akurat

import org.koin.dsl.module

fun profileModule(env: String? = null) =
    when (env) {
        "ci", "prod" -> {
            println("NOT MOCKED SERVICES $env")
            profilesCassandraModule
        }
        else -> {
            println("MOCKED SERVICES $env")
            profilesInMemoryModule
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
