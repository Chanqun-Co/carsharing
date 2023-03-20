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

        fun createByReservation(product: Product, reservation: Reservation): Schedule {
            return Schedule(product = product, reservation = reservation, startTime = reservation.checkIn, endTime = reservation.checkOut, type = ScheduleType.RESERVATION).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }

        fun createByHost(product: Product, startTime: OffsetDateTime, endTime: OffsetDateTime): Schedule {
            require(checkTimeOnTheHour(startTime))
            require(checkTimeOnTheHour(endTime))
            require(endTime >= startTime.plusHours(MINIMUM_ARRANGED_HOUR))

            return Schedule(product = product, startTime = startTime, endTime = endTime, type = ScheduleType.HOST).apply {
                this.registerEvent(ScheduleCreatedEvent(this))
            }
        }
        private fun checkTimeOnTheHour(time: OffsetDateTime): Boolean {
            return time.minute == 0 && time.second == 0 && time.nano == 0
        }
    }
}
