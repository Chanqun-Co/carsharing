package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.ProductStatus
import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.reservation.domain.ReservationStatus.PENDING
import io.sharing.server.core.schedule.domain.Schedule.Companion.MINIMUM_RESERVATION_TIME
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE
import java.time.OffsetDateTime

class ReservationTest {
    @Test
    fun `예약 생성`() {
        val guest = createUser()
        val host = createUser()
        val product = createProduct()
        val checkin = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0)
        val checkout = OffsetDateTime.now().plusHours(MINIMUM_RESERVATION_TIME).withMinute(0).withSecond(0).withNano(0)

        val reservation = Reservation.create(guest, host, product, checkin, checkout)

        assertThat(reservation.guest).isEqualTo(guest)
        assertThat(reservation.host).isEqualTo(host)
        assertThat(reservation.product).isEqualTo(product)
        assertThat(reservation.checkIn).isEqualTo(checkin)
        assertThat(reservation.checkOut).isEqualTo(checkout)
        assertThat(reservation.status).isEqualTo(PENDING)
    }

    @EnumSource(value = ProductStatus::class, names = ["AVAILABLE"], mode = EXCLUDE)
    @ParameterizedTest
    fun `예약 생성 실패 - Product의 상태가 UNAVAILABLE일 때`(status: ProductStatus) {
        val product = createProduct(status = status)

        assertThatIllegalStateException().isThrownBy {
            createReservation(product = product)
        }
    }
}

fun createReservation(
    guest: User = createUser(email = "juhan211@naver.com", firstName = "Juhan", lastName = "Byeon"),
    host: User = createUser(),
    product: Product = createProduct(),
    checkIn: OffsetDateTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0),
    checkOut: OffsetDateTime = OffsetDateTime.now().plusHours(4).withMinute(0).withSecond(0).withNano(0)
): Reservation {
    return Reservation.create(guest, host, product, checkIn, checkOut)
}
