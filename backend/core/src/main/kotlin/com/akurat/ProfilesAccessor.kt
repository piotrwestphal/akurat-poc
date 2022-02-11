package com.akurat

import com.akurat.model.Profile
import io.ktor.features.*
import java.util.concurrent.ConcurrentHashMap

class ProfilesAccessor: ProfilesService {

    private val map = ConcurrentHashMap<String, Profile>()

    override fun create(name: String): Profile {
        val profile = Profile(name)
        map[name] = profile
        return profile
    }

    override fun get(name: String): Profile = map[name] ?: throw NotFoundException("Profile with name '$name' not found")

    override fun getAll(): List<Profile> = map.toList().map { it.second }
}