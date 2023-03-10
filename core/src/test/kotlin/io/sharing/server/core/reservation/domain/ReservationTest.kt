package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.ProductStatus.*
import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class ReservationTest {
    @Test
    fun `예약 생성`() {
        val guest = createUser()
        val host = createUser()
        val product = createProduct()
        val checkin = OffsetDateTime.now()
        val checkout = OffsetDateTime.now().plusHours(Reservation.MINIMUM_RESERVATION_TIME)

        val reservation = Reservation.create(guest, host, product, checkin, checkout)

        assertThat(reservation.guest).isEqualTo(guest)
        assertThat(reservation.host).isEqualTo(host)
        assertThat(reservation.product).isEqualTo(product)
        assertThat(reservation.checkIn).isEqualTo(checkin)
        assertThat(reservation.checkOut).isEqualTo(checkout)
        assertThat(reservation.status).isEqualTo(ReservationStatus.PENDING)
    }

    @Test
    fun `예약 생성 실패 - 체크아웃 날짜는 체크인 2시간 이전 일 때`() {
        val checkIn = OffsetDateTime.now()
        val checkOut = checkIn.plusHours(Reservation.MINIMUM_RESERVATION_TIME).minusNanos(1)

        assertThatIllegalArgumentException().isThrownBy {
            createReservation(checkIn = checkIn, checkOut = checkOut)
        }
    }

    @Test
    fun `예약 생성 실패 - Product의 상태가 UNAVAILABLE일 때`() {
        val product = createProduct(status = UNAVAILABLE)

        assertThatIllegalArgumentException().isThrownBy {
            createReservation(product = product)
        }
    }
}

fun createReservation(
    guest: User = createUser(email = "juhan211@naver.com", firstName = "Juhan", lastName = "Byeon"),
    host: User = createUser(),
    product: Product = createProduct(),
    checkIn: OffsetDateTime = OffsetDateTime.now(),
    checkOut: OffsetDateTime = OffsetDateTime.now().plusHours(4)
): Reservation {
    return Reservation.create(guest, host, product, checkIn, checkOut)
}
