package io.sharing.server.core.reservation.domain

/**
 * 호스트의 경우
 * PENDING -> APPROVED
 *         -> DISAPPROVED
 *
 * CANCELLATION_REQUEST -> CANCELED
 *
 * 게스트의 경우
 * APPROVED -> CANCELLATION_REQUEST
 */
enum class ReservationStatus(vararg status: ReservationStatus) {
    /** 거절 */
    DISAPPROVED(),

    /** 취소됨 */
    CANCELED(),

    /** 취소 요청 */
    CANCELLATION_REQUEST(CANCELED),

    /** 승인 */
    APPROVED(CANCELLATION_REQUEST),

    /** 승인 대기 */
    PENDING(APPROVED, DISAPPROVED);

    private val nextStatuses = status.toList()

    fun isAbleToChangeTo(status: ReservationStatus): Boolean {
        return status in nextStatuses
    }
}
