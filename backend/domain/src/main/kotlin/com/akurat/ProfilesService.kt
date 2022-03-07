package com.akurat

import com.akurat.model.Profile
import com.akurat.model.Role

interface ProfilesService {
    fun create(name: String, role: Role): Profile
    fun get(id: Long): Profile?
    fun getAll(): List<Profile>
    fun delete(id: Long): Profile?
}