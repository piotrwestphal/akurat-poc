package com.akurat

import org.koin.dsl.module

fun profileModule(env: String? = null) = listOf(
    module {
        single<ProfilesService> { ProfilesAccessor(get()) }
        when (env) {
            "ci", "prod" -> {
                println("NOT MOCKED SERVICES $env")
                cassandraModule
                single { ProfileDaoFactory(get()).dao() }
                single<ProfilesRepository> { CassandraProfilesRepository(get()) }
            }
            else -> {
                println("MOCKED SERVICES $env")
                single<ProfilesRepository> { InMemoryProfilesRepository() }
            }
        }
    },
)
