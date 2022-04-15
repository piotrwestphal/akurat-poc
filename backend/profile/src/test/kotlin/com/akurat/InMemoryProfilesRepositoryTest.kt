package com.akurat

import kotlin.test.BeforeTest
import kotlin.test.Test

// TODO: add tests?
internal class InMemoryProfilesRepositoryTest {

    private lateinit var profilesRepository: ProfilesRepository

    @BeforeTest
    fun setup() {
        profilesRepository = InMemoryProfilesRepository()
    }

    @Test
    fun `should create profile`() {

    }
}