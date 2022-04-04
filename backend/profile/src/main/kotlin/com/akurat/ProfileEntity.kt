package com.akurat

import com.akurat.model.Profile
import com.akurat.model.Role
import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import java.time.Instant
import java.util.*

@Entity
@CqlName("profiles")
internal data class ProfileEntity(
    @PartitionKey val profileId: UUID?,
    @ClusteringColumn val createdAt: Instant?,
    val name: String?,
    val role: String?,
    val updatedAt: Instant?,
)

internal fun Profile.toEntity() = ProfileEntity(profileId = id, name = name, role = role.name, createdAt = createdAt, updatedAt = updatedAt)

internal fun ProfileEntity.toProfile() = Profile(profileId!!, name!!, Role.valueOf(role!!), createdAt!!, updatedAt!!)