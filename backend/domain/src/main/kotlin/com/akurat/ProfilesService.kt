package com.akurat

import com.akurat.model.Profile
import com.akurat.model.ProfileUpdate
import com.akurat.model.Role
import java.util.*

interface ProfilesService {
    fun create(name: String, role: Role): Profile

    fun get(id: UUID): Profile?

    fun getAll(): List<Profile>

    fun update(id: UUID, profileUpdate: ProfileUpdate): Profile?

    fun delete(id: UUID): Profile?
}