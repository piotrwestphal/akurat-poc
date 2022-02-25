package com.akurat

import com.akurat.model.Profile
import io.github.serpro69.kfaker.Faker
import io.ktor.features.*
import java.util.concurrent.ConcurrentHashMap

class ProfilesAccessor: ProfilesService {

    private val map = ConcurrentHashMap<String, Profile>()

    override fun create(role: String): Profile {
        val faker = Faker()
        val name = faker.name.nameWithMiddle()
        val profile = Profile(role, name)
        map[name] = profile
        return profile
    }

    override fun get(name: String): Profile = map[name] ?: throw NotFoundException("Profile with name '$name' not found")

    override fun getAll(): List<Profile> = map.toList().sortedBy { pair -> pair.first }.map { it.second }
}