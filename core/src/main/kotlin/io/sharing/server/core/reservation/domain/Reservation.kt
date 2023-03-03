package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import java.time.OffsetDateTime

/**
 * 예약
 */
class Reservation(
    /** 등록차량 정보 */
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,

    /** 시작일 */
    var checkIn: LocalDateTime,

    /** 종료일 */
    var checkOut: LocalDateTime,

    /** 예약상태 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var status: ReservationStatus = ReservationStatus.UNAPPROVED,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<Reservation>() {
    companion object {
        fun create(product: Product, checkIn: LocalDateTime, checkOut: LocalDateTime): Reservation {
            require(checkOut >= checkIn.plusHours(2))

            return Reservation(product, checkIn, checkOut)
        }
    }
}
