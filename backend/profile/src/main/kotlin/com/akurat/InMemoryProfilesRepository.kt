package com.akurat

import com.akurat.model.Profile

internal class InMemoryProfilesRepository : ProfilesRepository, InMemoryRepository<Profile>() {
    override fun findAll(): List<Profile> = super.findAll().sortedBy(Profile::createdAt)
}