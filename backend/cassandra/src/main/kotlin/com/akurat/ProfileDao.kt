package com.akurat

import com.datastax.oss.driver.api.core.PagingIterable
import com.datastax.oss.driver.api.mapper.annotations.Dao
import com.datastax.oss.driver.api.mapper.annotations.Insert
import com.datastax.oss.driver.api.mapper.annotations.Select
import java.util.*

@Dao
interface ProfileDao {
    @Insert
    fun create(profile: ProfileEntity): ProfileEntity

    @Select
    fun get(profileId: UUID): ProfileEntity?

    @Select
    fun getAll(): PagingIterable<ProfileEntity>
}