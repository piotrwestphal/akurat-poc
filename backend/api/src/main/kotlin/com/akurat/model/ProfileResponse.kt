package com.akurat.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(val name: String)

fun Profile.toResponse() = ProfileResponse(name)