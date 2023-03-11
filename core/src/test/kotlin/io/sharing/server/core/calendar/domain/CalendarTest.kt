package io.sharing.server.core.calendar.domain

import io.sharing.server.core.calendar.domain.CalendarType.*
import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.reservation.domain.createReservation
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class CalendarTest {
    @Test
    fun `캘린더 생성 - 예약 생성시`() {
        val product = createProduct()
        val reservation = createReservation()

        val calendar = Calendar.create(product = product, reservation = reservation, startTime = reservation.checkIn, endTime = reservation.checkOut, calendarType = RESERVE)

        assertThat(calendar.product).isEqualTo(product)
        assertThat(calendar.reservation).isEqualTo(reservation)
        assertThat(calendar.startTime).isEqualTo(reservation.checkIn)
        assertThat(calendar.endTime).isEqualTo(reservation.checkOut)
        assertThat(calendar.type).isEqualTo(RESERVE)
    }

    @Test
    fun `캘린더 생성 - 불가능한 날짜`() {
        val product = createProduct()
        val startTime = OffsetDateTime.now()
        val endTime = OffsetDateTime.now()

        val calendar = Calendar.create(product = product, startTime = startTime, endTime = endTime, calendarType = IMPOSSIBLE)

        assertThat(calendar.product).isEqualTo(product)
        assertThat(calendar.reservation).isNull()
        assertThat(calendar.startTime).isEqualTo(startTime)
        assertThat(calendar.endTime).isEqualTo(endTime)
        assertThat(calendar.type).isEqualTo(IMPOSSIBLE)
    }
}
