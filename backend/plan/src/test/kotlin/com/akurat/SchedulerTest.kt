package com.akurat

import com.akurat.model.ZonedDatePeriod
import io.github.serpro69.kfaker.Faker
import io.ktor.features.*
import java.time.ZonedDateTime
import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

internal class SchedulerTest {

    @Test
    fun `should not be available if a schedule already exist`() {
        val scheduler = Scheduler()
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        scheduler.setBusy("name", period)
        val result = scheduler.isAvailable("name", period)
        val faker = Faker()
        faker.name.nameWithMiddle()
        assertThat(result).isFalse
        assertThat(faker.name.name()).isEqualTo("John")
    }

    @Test
    fun `should throw exception if a schedule already exist`() {
        val scheduler = Scheduler()
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        scheduler.setBusy("name", period)
        assertThatThrownBy { scheduler.setBusy("name", period) }
            .isExactlyInstanceOf(BadRequestException::class.java)
    }

    @Test
    fun `should be available if there is no schedule`() {
        val scheduler = Scheduler()
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        val result = scheduler.isAvailable("name", period)
        assertThat(result).isTrue
    }

    @Test
    fun `should throw exception if there is no schedule`() {
        val scheduler = Scheduler()
        val period = ZonedDatePeriod(ZonedDateTime.now(), ZonedDateTime.now(), "")
        assertThatThrownBy { scheduler.setAvailable("name", period) }
            .isExactlyInstanceOf(BadRequestException::class.java)
    }
}