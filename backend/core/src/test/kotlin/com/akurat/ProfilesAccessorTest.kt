package com.akurat

import kotlin.test.BeforeTest
import kotlin.test.Test

internal class ProfilesAccessorTest {

    private lateinit var profilesService: ProfilesService

    @BeforeTest
    fun setup() {
        profilesService = ProfilesAccessor()
    }

    @Test
    fun `should create profile`() {

    }
}