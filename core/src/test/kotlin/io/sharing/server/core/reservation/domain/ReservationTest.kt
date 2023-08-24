package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.ProductStatus
import io.sharing.server.core.product.domain.createProduct
import io.sharing.server.core.reservation.domain.ReservationStatus.*
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE

class ReservationTest {
    @Test
    fun `예약 생성`() {
        val guest = createUser("juhan@gmail.com", "Juhan", "Byeon")
        val host = createUser()
        val product = createProduct()

        val reservation = Reservation.create(guest, host, product)

        assertThat(reservation.guest).isEqualTo(guest)
        assertThat(reservation.host).isEqualTo(host)
        assertThat(reservation.product).isEqualTo(product)
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

    @Test
    fun `예약 승인`() {
        val reservation = createReservation()

        reservation.approve()

        assertThat(reservation.status).isEqualTo(APPROVED)
        assertThat(reservation.updatedAt).isNotNull
    }

    @ParameterizedTest
    @EnumSource(value = ReservationStatus::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
    fun `예약 승인 실패`(status: ReservationStatus) {
        val reservation = createReservation(status = status)

        assertThatIllegalStateException().isThrownBy {
            reservation.approve()
        }
    }

    @Test
    fun `예약 거절`() {
        val reservation = createReservation()

        reservation.reject()

        assertThat(reservation.status).isEqualTo(REJECTED)
        assertThat(reservation.updatedAt).isNotNull
    }

    @ParameterizedTest
    @EnumSource(value = ReservationStatus::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
    fun `예약 거절 실패`(status: ReservationStatus) {
        val reservation = createReservation(status = status)

        assertThatIllegalStateException().isThrownBy {
            reservation.reject()
        }
    }

    @Test
    fun `예약 취소 요청`() {
        val reservation = createReservation(status = APPROVED)

        reservation.requestCancel()

        assertThat(reservation.status).isEqualTo(REQUEST_CANCEL)
        assertThat(reservation.updatedAt).isNotNull
    }

    @ParameterizedTest
    @EnumSource(value = ReservationStatus::class, names = ["APPROVED"], mode = EnumSource.Mode.EXCLUDE)
    fun `예약 취소 요청 실패`(status: ReservationStatus) {
        val reservation = createReservation(status = status)

        assertThatIllegalStateException().isThrownBy {
            reservation.requestCancel()
        }
    }

    @Test
    fun `예약 취소`() {
        val reservation = createReservation(status = REQUEST_CANCEL)

        reservation.cancel()

        assertThat(reservation.status).isEqualTo(CANCELED)
        assertThat(reservation.updatedAt).isNotNull
    }

    @ParameterizedTest
    @EnumSource(value = ReservationStatus::class, names = ["CANCELLATION_REQUEST"], mode = EnumSource.Mode.EXCLUDE)
    fun `예약 취소 실패`(status: ReservationStatus) {
        val reservation = createReservation(status = status)

        assertThatIllegalStateException().isThrownBy {
            reservation.cancel()
        }
    }
}

fun createReservation(
    guest: User = createUser(email = "juhan211@naver.com", firstName = "Juhan", lastName = "Byeon"),
    host: User = createUser(),
    product: Product = createProduct(),
    status: ReservationStatus = PENDING
): Reservation {
    return Reservation.create(guest, host, product).apply {
        this.status = status
    }
}
