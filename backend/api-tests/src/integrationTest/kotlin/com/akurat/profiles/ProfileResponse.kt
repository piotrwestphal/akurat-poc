package com.akurat.profiles

import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileResponse(
    val id: String,
    val name: String,
    val role: String,
    val createdAt: Long,
    val updatedAt: Long
)