package com.akurat

import com.akurat.model.Profile
import io.ktor.features.*

internal class AppService(
    private val profilesService: ProfilesService
) {
    fun getProfile(name: String): Profile = profilesService.get(name)
        ?: throw NotFoundException("Profile with name '$name' not found")

    fun createProfile(name: String): Profile = profilesService.create(name)

    fun getProfiles(): List<Profile> = profilesService.getAll()
}