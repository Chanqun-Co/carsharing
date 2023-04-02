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
    fun `예약 상태 변경`() {
        val reservation = createReservation()
        val status = APPROVED

        reservation.changeStatus(status)

        assertThat(reservation.status).isEqualTo(status)
    }

    @Test
    fun `예약 상태 변경 실패`() {
        val reservation = createReservation()
        val status = CANCELED

        assertThatIllegalStateException().isThrownBy {
            reservation.changeStatus(status)
        }
    }
}

fun createReservation(
    guest: User = createUser(email = "juhan211@naver.com", firstName = "Juhan", lastName = "Byeon"),
    host: User = createUser(),
    product: Product = createProduct(),
): Reservation {
    return Reservation.create(guest, host, product)
}
