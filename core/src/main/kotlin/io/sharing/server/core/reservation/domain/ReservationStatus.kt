package io.sharing.server.core.reservation.domain

enum class ReservationStatus {
    /** 승인 */
    APPROVED,

    /** 승인 대기 */
    APPROVAL_PENDING,

    /** 승인 거절 */
    UNAPPROVED,

    /** 취소 요청 */
    CANCELLATION_REQUEST,

    /** 취소 */
    CANCELED
}
