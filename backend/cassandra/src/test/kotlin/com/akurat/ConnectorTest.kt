package com.akurat
import kotlin.test.Test

// TODO: remove
internal class ConnectorTest {

    @Test
    fun connect() {
        val connector = Connector()
        connector.healthCheck()
    }
}