package com.akurat.model

import java.time.Instant
import java.util.*

data class Profile(
    override val id: UUID,
    val name: String,
    val role: Role,
    val createdAt: Instant,
    val updatedAt: Instant
) : DBRecord
