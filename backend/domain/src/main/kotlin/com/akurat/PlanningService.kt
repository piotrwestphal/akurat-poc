package com.akurat

import com.akurat.model.ZonedDatePeriod

interface PlanningService {
    fun isAvailable(name: String, period: ZonedDatePeriod): Boolean
    fun setAvailable(name: String, period: ZonedDatePeriod)
    fun setBusy(name: String, period: ZonedDatePeriod)
    fun getSchedule(name: String, period: ZonedDatePeriod): List<ZonedDatePeriod>
}