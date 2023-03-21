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
 *
 * 스케줄에 없는 시간은 예약 가능한 시간
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
        const val MINIMUM_ARRANGED_HOUR = 1L
        const val MINIMUM_RESERVATION_TIME = 2L

        fun createReservedSchedule(product: Product, reservation: Reservation): Schedule {
            with(reservation) {
                require(checkIn.isOnTheHour())
                require(checkOut.isOnTheHour())
                require(checkOut >= checkIn.plusHours(MINIMUM_RESERVATION_TIME))
            }

            return Schedule(product = product, reservation = reservation, startTime = reservation.checkIn, endTime = reservation.checkOut, type = ScheduleType.RESERVED).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }

        fun createBlockedSchedule(product: Product, startTime: OffsetDateTime, endTime: OffsetDateTime): Schedule {
            require(startTime.isOnTheHour())
            require(endTime.isOnTheHour())
            require(endTime >= startTime.plusHours(MINIMUM_ARRANGED_HOUR))

            return Schedule(product = product, startTime = startTime, endTime = endTime, type = ScheduleType.BLOCKED).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }
    }
}

fun OffsetDateTime.isOnTheHour(): Boolean {
    return this.minute == 0 && this.second == 0 && this.nano == 0
}
