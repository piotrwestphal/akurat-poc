package com.akurat

import com.datastax.oss.driver.api.core.CqlSession

class CassandraTestHelper {
    fun executeScriptsFromFile(fileName: String) {
        getSession().use { session ->
            StatementsExtractor.extract(fileName).forEach {
                println("Executing cql statement: $it")
                session.execute(it)
            }
        }
    }

    fun executeScript(cqlQuery: String, keyspace: String? = null) {
        getSession(keyspace).use {
            println("Executing cql statement: $cqlQuery")
            it.execute(cqlQuery)
        }
    }

    private fun getSession(keyspace: String? = null) = CqlSession.builder()
        .withKeyspace(keyspace)
        .build()
}