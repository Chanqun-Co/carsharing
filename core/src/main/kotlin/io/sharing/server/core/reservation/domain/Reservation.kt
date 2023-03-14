package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.ProductStatus
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import io.sharing.server.core.user.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime

/**
 * 예약
 */
@Entity
class Reservation(

    /** 호스트 */
    @ManyToOne(fetch = FetchType.LAZY)
    val host: User,

    /** 게스트 */
    @ManyToOne(fetch = FetchType.LAZY)
    val guest: User,

    /** 상품 정보 */
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,

    /** 체크인 */
    var checkIn: OffsetDateTime,

    /** 체크아웃 */
    var checkOut: OffsetDateTime,

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var status: ReservationStatus = ReservationStatus.PENDING,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<Reservation>() {

    companion object {
        const val MINIMUM_RESERVATION_TIME = 2L

        fun create(guest: User, host: User, product: Product, checkIn: OffsetDateTime, checkOut: OffsetDateTime): Reservation {
            require(product.status == ProductStatus.AVAILABLE)
            require(checkOut >= checkIn.plusHours(MINIMUM_RESERVATION_TIME))

            return Reservation(guest, host, product, checkIn, checkOut).apply {
                this.registerEvent(ReservationCreatedEvent(this))
            }
        }
    }
}
