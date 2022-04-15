package com.akurat

import com.akurat.model.DBRecord
import java.util.*
import java.util.concurrent.ConcurrentHashMap

// TODO: move somewhere else -> separate 'common' module
abstract class InMemoryRepository<T: DBRecord> {

    private val store = ConcurrentHashMap<UUID, T>()

    open fun insert(record: T): T {
        store[record.id] = record
        return record
    }

    open fun findById(id: UUID): T? = store[id]

    open fun findAll(): List<T> = store.toList().map { it.second }

    open fun update(id: UUID, record: T): T {
        store[id] = record
        return record
    }

    open fun findAndDelete(id: UUID): T? = store.remove(id)
}
