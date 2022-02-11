package com.akurat

import com.akurat.model.ZonedDatePeriod
import io.ktor.features.*
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

class Scheduler : PlanningService {

    private val map = ConcurrentHashMap<String, MutableMap<ZonedDateTime, ZonedDateTime>>()

    override fun isAvailable(name: String, period: ZonedDatePeriod): Boolean =
        map.getOrElse(name) { mutableMapOf() }.let { !it.containsKey(period.start) }

    override fun setAvailable(name: String, period: ZonedDatePeriod) {
        val schedule = map[name] ?: throw BadRequestException("Schedule for name '$name' not found")
        schedule.remove(period.start)
    }

    override fun setBusy(name: String, period: ZonedDatePeriod) {
        if (map.getOrPut(name) { mutableMapOf() }.containsKey(period.start)) {
            throw BadRequestException("Schedule for name '$name' and period start '${period.start}' already exist")
        }
        map[name]!![period.start] = period.end
    }

    override fun getSchedule(name: String, period: ZonedDatePeriod): List<ZonedDatePeriod> =
        map[name]?.entries?.map { ZonedDatePeriod(it.key, it.value, null) } ?: emptyList()
}