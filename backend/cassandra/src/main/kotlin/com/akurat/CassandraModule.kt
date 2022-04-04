package com.akurat

import com.datastax.oss.driver.api.core.CqlSession
import org.koin.dsl.module

val cassandraModule = module {
    factory { CqlSession.builder().build() }
}