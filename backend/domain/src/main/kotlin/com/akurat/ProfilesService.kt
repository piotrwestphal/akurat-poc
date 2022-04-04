package com.akurat

import com.akurat.model.Profile
import com.akurat.model.Role
import java.util.*

interface ProfilesService {
    fun create(name: String, role: Role): Profile

    fun get(id: UUID): Profile?

    fun getAll(): List<Profile>

    fun update(id: UUID, profile: Profile): Profile?

    fun delete(id: UUID): Profile?
}