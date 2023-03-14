package io.sharing.server.core.schedule.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.reservation.domain.createReservation
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ScheduleTest {
    @Test
    fun `스케줄 생성 - RESERVED`() {
        val product = createProduct()
        val reservation = createReservation()

        val calendar = Schedule.createReserved(product = product, reservation = reservation)

        assertThat(calendar.product).isEqualTo(product)
        assertThat(calendar.reservation).isEqualTo(reservation)
        assertThat(calendar.startTime).isEqualTo(reservation.checkIn)
        assertThat(calendar.endTime).isEqualTo(reservation.checkOut)
        assertThat(calendar.type).isEqualTo(ScheduleType.RESERVED)
    }

    @Test
    fun `스케줄 생성 - UNAVAILABLE`() {
        val product = createProduct()
        val startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val endTime = startTime.plusHours(1)

        val calendar = createUnavailableSchedule(product = product, startTime = startTime, endTime = endTime)

        assertThat(calendar.product).isEqualTo(product)
        assertThat(calendar.reservation).isNull()
        assertThat(calendar.startTime).isEqualTo(startTime)
        assertThat(calendar.endTime).isEqualTo(endTime)
        assertThat(calendar.type).isEqualTo(ScheduleType.UNAVAILABLE)
    }
}

fun createUnavailableSchedule(
    product: Product = createProduct(),
    startTime: OffsetDateTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0),
    endTime: OffsetDateTime = OffsetDateTime.now().plusHours(4)
): Schedule {
    return Schedule.createUnavailable(product, startTime, endTime)
}
