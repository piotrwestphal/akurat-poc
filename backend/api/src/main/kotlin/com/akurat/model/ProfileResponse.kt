package com.akurat.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(val role: String, val name: String)

fun Profile.toResponse() = ProfileResponse(role, name)