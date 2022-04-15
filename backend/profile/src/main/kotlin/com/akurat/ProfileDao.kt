package com.akurat

import com.datastax.oss.driver.api.core.PagingIterable
import com.datastax.oss.driver.api.mapper.annotations.*
import java.util.*

@Dao
internal interface ProfileDao {
    @Insert
    fun insert(profile: ProfileEntity)

    @Select
    fun findById(profileId: UUID): ProfileEntity?

    @Select
    fun findAll(): PagingIterable<ProfileEntity>

    @Update
    fun update(profile: ProfileEntity)

    @Delete
    fun delete(profile: ProfileEntity)
}