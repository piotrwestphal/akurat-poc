package com.akurat

import com.akurat.model.Profile
import com.akurat.model.ProfileUpdate
import com.akurat.model.Role
import java.time.Instant
import java.util.*

internal class ProfilesAccessor(private val profilesRepository: ProfilesRepository) : ProfilesService {

    override fun create(name: String, role: Role) =
        Profile(UUID.randomUUID(), name, role, Instant.now(), Instant.now())
            .also { profilesRepository.insert(it) }

    override fun get(id: UUID): Profile? = profilesRepository.findById(id)

    override fun getAll(): List<Profile> = profilesRepository.findAll()

    override fun update(id: UUID, profileUpdate: ProfileUpdate): Profile? {
        val (name, role) = profileUpdate
        return profilesRepository.findById(id)?.let {
            it.copy(
                updatedAt = Instant.now(),
                name = name ?: it.name,
                role = role ?: it.role,
            )
        }
    }

    override fun delete(id: UUID): Profile? = profilesRepository.findAndDelete(id)
}