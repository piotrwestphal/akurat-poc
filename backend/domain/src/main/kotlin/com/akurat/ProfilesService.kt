package com.akurat

import com.akurat.model.Profile

interface ProfilesService {
    fun create(name: String): Profile
    fun get(name: String): Profile?
    fun getAll(): List<Profile>
}