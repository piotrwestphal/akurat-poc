package com.akurat

import com.datastax.oss.driver.api.core.CqlSession

internal class ProfileDaoFactory(
    private val cqlSession: CqlSession
) {
    fun dao() = ProfileMapper.builder(cqlSession).build().dao()
}