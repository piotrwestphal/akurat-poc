package com.akurat

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import java.time.Instant
import java.util.*

@Entity
@CqlName("profiles")
data class ProfileEntity(
    @PartitionKey val profileId: UUID?,
    val name: String?,
    val role: String?,
    val createdAt: Instant?,
)