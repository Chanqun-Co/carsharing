package io.sharing.server.core.reservation.domain

/**
 * 호스트의 경우
 * PENDING -> APPROVED
 *         -> DISAPPROVED
 *
 * REQUEST_CANCEL -> CANCELED
 *
 * 게스트의 경우
 * APPROVED -> REQUEST_CANCEL
 */
enum class ReservationStatus(vararg status: ReservationStatus) {
    /** 거절 */
    REJECTED(),

    /** 취소됨 */
    CANCELED(),

    /** 취소 요청 */
    REQUEST_CANCEL(CANCELED),

    /** 승인 */
    APPROVED(REQUEST_CANCEL),

    /** 승인 대기 */
    PENDING(APPROVED, REJECTED);

    private val nextStatuses = status.toList()

    fun canChangeTo(status: ReservationStatus): Boolean {
        return status in nextStatuses
    }
}
