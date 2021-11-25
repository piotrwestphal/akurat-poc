package com.akurat.profile

import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequest(val text: String)
