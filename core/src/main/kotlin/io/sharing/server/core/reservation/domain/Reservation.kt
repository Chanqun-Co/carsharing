package io.sharing.server.core.reservation.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.product.domain.ProductStatus.*
import io.sharing.server.core.reservation.domain.ReservationStatus.*
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

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var status: ReservationStatus = PENDING,

    /** 상태변경일시 */
    var updatedAt: OffsetDateTime? = null,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<Reservation>() {
    fun approve() {
        check(status.canChangeTo(APPROVED))

        this.updatedAt = OffsetDateTime.now()
        this.status = APPROVED
    }

    fun reject() {
        check(status.canChangeTo(ReservationStatus.REJECTED))

        this.updatedAt = OffsetDateTime.now()
        this.status = ReservationStatus.REJECTED
    }

    fun requestCancel() {
        check(status.canChangeTo(REQUEST_CANCEL))

        this.updatedAt = OffsetDateTime.now()
        this.status = REQUEST_CANCEL
    }

    fun cancel() {
        check(status.canChangeTo(CANCELED))

        this.updatedAt = OffsetDateTime.now()
        this.status = CANCELED
    }

    companion object {
        fun create(guest: User, host: User, product: Product): Reservation {
            check(product.status == AVAILABLE)

            return Reservation(guest, host, product).apply {
                this.registerEvent(ReservationCreatedEvent(this))
            }
        }
    }
}
