package com.akurat

import com.akurat.model.Profile
import java.util.*

internal interface ProfilesRepository {
    fun insert(record: Profile): Profile
    fun findById(id: UUID): Profile?
    fun findAll(): List<Profile>
    fun update(id: UUID, record: Profile): Profile
    fun findAndDelete(id: UUID): Profile?
}