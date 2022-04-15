package com.akurat.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val id: String,
    val name: String,
    val role: String,
    val createdAt: Long,
    val updatedAt: Long
)

fun Profile.toResponse() = ProfileResponse(
    id = id.toString(),
    name = name,
    role = role.name,
    createdAt = createdAt.toEpochMilli(),
    updatedAt = updatedAt.toEpochMilli()
)