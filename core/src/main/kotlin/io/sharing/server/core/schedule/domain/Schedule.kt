package io.sharing.server.core.schedule.domain

import io.sharing.server.core.product.domain.Product
import io.sharing.server.core.reservation.domain.Reservation
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.time.OffsetDateTime

/**
 * 스케줄
 * 상품에 대한 예약 불가능한 기간 집합
 */
@Entity
class Schedule(

    /** 상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,

    /** 예약 */
    @OneToOne(fetch = FetchType.LAZY)
    var reservation: Reservation? = null,

    /** 시작 시간 */
    var startTime: OffsetDateTime,

    /** 끝 시간 */
    var endTime: OffsetDateTime,

    /** 체크타입 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var type: ScheduleType,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<Schedule>() {

    companion object {
        const val MINIMUM_ARRANGED_TIME = 1L

        fun createReserved(product: Product, reservation: Reservation): Schedule {

            return Schedule(product = product, reservation = reservation, startTime = reservation.checkIn, endTime = reservation.checkOut, type = ScheduleType.RESERVED).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }

        fun createUnavailable(product: Product, startTime: OffsetDateTime, endTime: OffsetDateTime): Schedule {
            require(startTime.minute == 0 && startTime.second == 0 && startTime.nano == 0)
            require(endTime.minute == 0 && endTime.second == 0 && endTime.nano == 0)
            require(endTime >= startTime.plusHours(MINIMUM_ARRANGED_TIME))

            return Schedule(product = product, startTime = startTime, endTime = endTime, type = ScheduleType.UNAVAILABLE).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }
    }
}
