package io.sharing.server.core.schedule.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.reservation.domain.createReservation
import io.sharing.server.core.schedule.domain.Schedule.Companion.MINIMUM_ARRANGED_HOUR
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

        val calendar = Schedule.createByReservation(product = product, reservation = reservation)

        assertThat(calendar.product).isEqualTo(product)
        assertThat(calendar.reservation).isEqualTo(reservation)
        assertThat(calendar.startTime).isEqualTo(reservation.checkIn)
        assertThat(calendar.endTime).isEqualTo(reservation.checkOut)
        assertThat(calendar.type).isEqualTo(ScheduleType.RESERVATION)
    }

    @Test
    fun `스케줄 생성 - UNAVAILABLE`() {
        val product = createProduct()
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = startTime.plusHours(1)

        val calendar = createScheduleByHost(product = product, startTime = startTime, endTime = endTime)

        assertThat(calendar.product).isEqualTo(product)
        assertThat(calendar.reservation).isNull()
        assertThat(calendar.startTime).isEqualTo(startTime)
        assertThat(calendar.endTime).isEqualTo(endTime)
        assertThat(calendar.type).isEqualTo(ScheduleType.HOST)
    }

    @ParameterizedTest
    @CsvSource("2023-03-15T12:00:00.001Z", "2023-03-15T12:01:00.000Z", "2023-03-15T12:00:01.000Z")
    fun `스케줄 생성 실패 - startTime가 정시가 아닐 경우`(startTime: OffsetDateTime) {
        assertThatIllegalArgumentException().isThrownBy {
            createScheduleByHost(startTime = startTime)
        }
    }

    @ParameterizedTest
    @CsvSource("2023-03-15T12:00:00.001Z", "2023-03-15T12:01:00.000Z", "2023-03-15T12:00:01.000Z")
    fun `스케줄 생성 실패 - endTime가 정시가 아닐 경우`(endTime: OffsetDateTime) {
        assertThatIllegalArgumentException().isThrownBy {
            createScheduleByHost(startTime = endTime.minusHours(MINIMUM_ARRANGED_HOUR), endTime = endTime)
        }
    }

    @Test
    fun `스케줄 생성 실패 - startTime과 endTime의 시간차가 1시간 이내일 경우`() {
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)

        assertThatIllegalArgumentException().isThrownBy {
            createScheduleByHost(startTime = startTime, endTime = endTime)
        }
    }
}

fun createScheduleByHost(
    product: Product = createProduct(),
    startTime: OffsetDateTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0),
    endTime: OffsetDateTime = OffsetDateTime.now().plusHours(4).withMinute(0).withSecond(0).withNano(0)
): Schedule {
    return Schedule.createByHost(product, startTime, endTime)
}
