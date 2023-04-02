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
 * 일정
 *
 * 상품에 대한 계획된 일정
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

    /** 타입 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var type: ScheduleType,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<Schedule>() {

    companion object {
        const val MINIMUM_BLOCKED_HOUR = 1L
        const val MINIMUM_RESERVABLE_HOUR = 2L

        fun createReservedSchedule(product: Product, reservation: Reservation, startTime: OffsetDateTime, endTime: OffsetDateTime): Schedule {
            check(isSharp(startTime))
            check(isSharp(endTime))
            check(endTime >= startTime.plusHours(MINIMUM_RESERVABLE_HOUR))

            return Schedule(product = product, reservation = reservation, startTime = startTime, endTime = endTime, type = ScheduleType.RESERVED).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }

        fun createBlockedSchedule(product: Product, startTime: OffsetDateTime, endTime: OffsetDateTime): Schedule {
            check(isSharp(startTime))
            check(isSharp(endTime))
            check(endTime >= startTime.plusHours(MINIMUM_BLOCKED_HOUR))

            return Schedule(product = product, startTime = startTime, endTime = endTime, type = ScheduleType.BLOCKED).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }

        private fun isSharp(time: OffsetDateTime): Boolean {
            return time.minute == 0 && time.second == 0 && time.nano == 0
        }
    }
}
