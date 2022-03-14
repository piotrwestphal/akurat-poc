package com.akurat.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ProfileResponse(val id: String, val name: String, val role: String, val createdAt: Long)

fun Profile.toResponse() = ProfileResponse(id.toString(), name, role.name, createdAt.toEpochMilli())