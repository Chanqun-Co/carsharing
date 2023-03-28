package io.sharing.server.core.schedule.domain

import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.reservation.domain.createReservation
import io.sharing.server.core.schedule.domain.Schedule.Companion.MINIMUM_BLOCKED_HOUR
import io.sharing.server.core.schedule.domain.Schedule.Companion.MINIMUM_RESERVABLE_HOUR
import io.sharing.server.core.schedule.domain.Schedule.Companion.createBlockedSchedule
import io.sharing.server.core.schedule.domain.Schedule.Companion.createReservedSchedule
import io.sharing.server.core.schedule.domain.ScheduleType.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.OffsetDateTime

class ScheduleTest {
    @Test
    fun `스케줄 생성 - RESERVED`() {
        val product = createProduct()
        val reservation = createReservation()
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = startTime.plusHours(MINIMUM_RESERVABLE_HOUR)

        val schedule = createReservedSchedule(product, reservation, startTime, endTime)

        assertThat(schedule.product).isEqualTo(product)
        assertThat(schedule.reservation).isEqualTo(reservation)
        assertThat(schedule.startTime).isEqualTo(startTime)
        assertThat(schedule.endTime).isEqualTo(endTime)
        assertThat(schedule.type).isEqualTo(RESERVED)
    }

    @Test
    fun `스케줄 생성 - BLOCKED`() {
        val product = createProduct()
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = startTime.plusHours(MINIMUM_BLOCKED_HOUR)

        val schedule = createBlockedSchedule(product, startTime, endTime)

        assertThat(schedule.product).isEqualTo(product)
        assertThat(schedule.reservation).isNull()
        assertThat(schedule.startTime).isEqualTo(startTime)
        assertThat(schedule.endTime).isEqualTo(endTime)
        assertThat(schedule.type).isEqualTo(BLOCKED)
    }

    @ParameterizedTest
    @CsvSource("2023-03-15T12:01:00.001Z", "2023-03-15T12:00:01.000Z", "2023-03-15T12:10:00.001Z")
    fun `스케줄 생성 실패 - startTime 정시가 아닐 경우`(startTime: OffsetDateTime) {
        val product = createProduct()
        val reservation = createReservation()
        val endTime = startTime.plusHours(MINIMUM_RESERVABLE_HOUR + 1).withMinute(0).withSecond(0).withNano(0)

        assertThatIllegalArgumentException().isThrownBy {
            createReservedSchedule(product, reservation, startTime, endTime)
        }
    }

    @ParameterizedTest
    @CsvSource("2023-03-15T12:01:00.001Z", "2023-03-15T12:00:01.000Z", "2023-03-15T12:10:00.001Z")
    fun `스케줄 생성 실패 - endTime 정시가 아닐 경우`(endTime: OffsetDateTime) {
        val product = createProduct()
        val reservation = createReservation()
        val startTime = endTime.minusHours(MINIMUM_RESERVABLE_HOUR + 1).withMinute(0).withSecond(0).withNano(0)

        assertThatIllegalArgumentException().isThrownBy {
            createReservedSchedule(product, reservation, startTime, endTime)
        }
    }

    @Test
    fun `스케줄 생성 실패 - RESERVED startTime, endTime 사이의 시간차가 2시간 이전 일 때`() {
        val product = createProduct()
        val reservation = createReservation()
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = startTime.plusHours(MINIMUM_RESERVABLE_HOUR).minusHours(1)

        assertThatIllegalArgumentException().isThrownBy {
            createReservedSchedule(product, reservation, startTime, endTime)
        }
    }

    @Test
    fun `스케줄 생성 실패 - startTime, endTime 사이의 시간차가 1시간 이내일 경우`() {
        val product = createProduct()
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = startTime.plusHours(MINIMUM_BLOCKED_HOUR).minusHours(1)

        assertThatIllegalArgumentException().isThrownBy {
            createBlockedSchedule(product = product, startTime = startTime, endTime = endTime)
        }
    }
}
