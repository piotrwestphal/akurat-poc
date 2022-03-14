package com.akurat

import com.akurat.model.Profile
import com.akurat.model.Role
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

class ProfilesAccessor : ProfilesService {

    private val map = ConcurrentHashMap<UUID, Profile>()

    override fun create(name: String, role: Role): Profile {
        val profile = Profile(UUID.randomUUID(), name, role, Instant.now())
        map[profile.id] = profile
        return profile
    }

    override fun get(id: UUID): Profile? = map[id]

    override fun getAll(): List<Profile> = map.toList().sortedBy { pair -> pair.first }.map { it.second }

    override fun delete(id: UUID): Profile? = map.remove(id)
}