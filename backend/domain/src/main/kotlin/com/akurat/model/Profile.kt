package com.akurat.model

import java.time.Instant

data class Profile(val id: Int, val name: String, val role: Role, val createdAt: Instant)
