package com.akurat

import com.akurat.model.ZonedDatePeriod
import io.ktor.features.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import java.time.ZonedDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class SchedulerTest {

    private lateinit var scheduler: Scheduler

    @BeforeTest
    fun setup() {
        scheduler = Scheduler()
    }

    @Test
    fun `should not be available if a schedule already exist`() {
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        scheduler.setBusy("name", period)
        val result = scheduler.isAvailable("name", period)
        assertThat(result).isFalse
    }

    @Test
    fun `should throw exception if a schedule already exist`() {
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        scheduler.setBusy("name", period)
        assertThatThrownBy { scheduler.setBusy("name", period) }
            .isExactlyInstanceOf(BadRequestException::class.java)
    }

    @Test
    fun `should be available if there is no schedule`() {
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        val result = scheduler.isAvailable("name", period)
        assertThat(result).isTrue
    }

    @Test
    fun `should throw exception if there is no schedule`() {
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        assertThatThrownBy { scheduler.setAvailable("name", period) }
            .isExactlyInstanceOf(BadRequestException::class.java)
    }
}