package com.akurat

import com.akurat.model.Profile

internal class AppService(
    private val profilesService: ProfilesService
) {
    fun getProfile(name: String): Profile = profilesService.get(name)

    fun createProfile(name: String): Profile = profilesService.create(name)

    fun getProfiles(): List<Profile> = profilesService.getAll()
}