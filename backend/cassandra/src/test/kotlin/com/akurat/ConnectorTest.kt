package com.akurat
import kotlin.test.Test


internal class ConnectorTest {

    @Test
    fun connect() {
        val connector = Connector()
        connector.healthCheck()
    }
}