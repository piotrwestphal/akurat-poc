package com.akurat.profile

import com.akurat.model.Role
import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequest(val name: String, val role: Role)
