package com.akurat.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(val id: Int, val name: String, val role: String, val createdAt: Long)

fun Profile.toResponse() = ProfileResponse(id, name, role.name, createdAt.toEpochMilli())