package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.createProduct
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime

class ReservationTest {
    @Test
    fun `예약 생성`() {
        // given
        val product = createProduct()
        val checkin = LocalDateTime.now()
        val checkout = LocalDateTime.now().plusHours(2L)

        // when
        val reservation = Reservation.create(product, checkin, checkout)

        // then
        assertThat(reservation.product).isEqualTo(product)
        assertThat(reservation.checkIn).isEqualTo(checkin)
        assertThat(reservation.checkOut).isEqualTo(checkout)
    }

    @ParameterizedTest
    @CsvSource("2023-01-05T13:00:00, 2023-01-05T10:00:00", "2023-01-05T12:00:00, 2023-01-05T13:30:00")
    fun `예약 생성 실패 - 체크아웃 날짜는 체크인 2시간 이후여야 한다`(checkIn: LocalDateTime, checkOut: LocalDateTime) {
        assertThatIllegalArgumentException().isThrownBy {
            createReservation(checkIn = checkIn, checkOut = checkOut)
        }
    }
}

fun createReservation(
    product: Product = createProduct(),
    checkIn: LocalDateTime = LocalDateTime.now(),
    checkOut: LocalDateTime = LocalDateTime.now().plusHours(4)
): Reservation {
    return Reservation.create(product, checkIn, checkOut)
}
