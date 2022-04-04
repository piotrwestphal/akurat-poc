package com.akurat

import mu.KLogging
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class TestServer {

    companion object: KLogging() {

        val dbHelper: CassandraTestHelper = CassandraTestHelper()

        @BeforeClass
        @JvmStatic
        internal fun createSchema() {
            dbHelper.executeScriptsFromFile("create_schema.cql")
        }

        @AfterClass
        @JvmStatic
        internal fun deleteSchema() {
            dbHelper.executeScriptsFromFile("delete_schema.cql")
        }
    }

    @BeforeTest
    fun before() {
        logger.info { "Clean up before test" }
        dbHelper.executeScriptsFromFile("clean_up.cql")
    }

    @AfterTest
    fun after() {
        logger.info { "Clean up after test" }
        dbHelper.executeScriptsFromFile("clean_up.cql")
    }
}