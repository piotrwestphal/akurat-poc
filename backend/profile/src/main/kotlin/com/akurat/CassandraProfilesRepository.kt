package com.akurat

import com.akurat.model.Profile
import java.util.*

internal class CassandraProfilesRepository(
    private val profileDao: ProfileDao
) : ProfilesRepository {

    override fun insert(record: Profile) =
        profileDao.insert(record.toEntity()).let { findById(record.id)!! }

    override fun findById(id: UUID) =
        profileDao.findById(id)?.toProfile()

    override fun findAll() =
        profileDao.findAll().all().map { it.toProfile() }

    override fun findAndUpdate(id: UUID, record: Profile) =
        profileDao.findById(id)?.let {
            profileDao.update(record.toEntity())
            record
        }

    override fun findAndDelete(id: UUID): Profile? =
        profileDao.findById(id)?.also { profileDao.delete(it) }?.toProfile()
}