package com.akurat

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.Mapper
import com.datastax.oss.driver.api.mapper.MapperBuilder

@Mapper
internal interface ProfileMapper {

    @DaoFactory
    fun dao(): ProfileDao

    companion object {
        fun builder(session: CqlSession): MapperBuilder<ProfileMapper> {
            return ProfileMapperBuilder(session).withDefaultKeyspace("kingboo")
        }
    }
}