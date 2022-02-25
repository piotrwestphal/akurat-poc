package com.akurat

import com.akurat.model.Profile

interface ProfilesService {
    fun create(role: String): Profile
    fun get(name: String): Profile
    fun getAll(): List<Profile>
}