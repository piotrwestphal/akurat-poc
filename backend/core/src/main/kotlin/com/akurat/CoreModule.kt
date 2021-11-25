package com.akurat

import org.koin.dsl.module

val coreModule = module {
    single<ProfilesService>{ProfilesAccessor()}
}