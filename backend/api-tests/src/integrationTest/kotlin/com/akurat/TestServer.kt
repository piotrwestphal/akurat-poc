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
            dbHelper.executeScripts("create_schema.cql")
        }

        @AfterClass
        @JvmStatic
        internal fun deleteSchema() {
            dbHelper.executeScripts("delete_schema.cql")
        }
    }

    @BeforeTest
    fun before() {
        logger.info { "Clean up before test" }
        dbHelper.executeScripts("clean_up.cql")
    }

    @AfterTest
    fun after() {
        logger.info { "Clean up after test" }
        dbHelper.executeScripts("clean_up.cql")
    }
}