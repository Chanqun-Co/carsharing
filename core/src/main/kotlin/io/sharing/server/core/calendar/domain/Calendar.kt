package io.sharing.server.core.calendar.domain

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
 * 캘린더
 * 상품에 대한 예약 불가능한 기간 집합
 */
@Entity
class Calendar(

    /** 상품 */
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,

    /** 예약 */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    val reservation: Reservation? = null,

    /** 예약불가 시작 시간 */
    var startTime: OffsetDateTime,

    /** 예약불가 끝 시간 */
    var endTime: OffsetDateTime,

    /** 체크타입 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var type: CalendarType,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<Calendar>() {

    companion object {
        fun create(product: Product, reservation: Reservation? = null, startTime: OffsetDateTime, endTime: OffsetDateTime, calendarType: CalendarType): Calendar {

            // TODO: 해당 상품의 예약리스트를 확인해서 중복되는 시간이 있는 경우 확인

            return Calendar(product = product, reservation = reservation, startTime = startTime, endTime = endTime, type = calendarType).apply {
                this.registerEvent(CalendarCreatedEvent(this))
            }
        }
    }
}
