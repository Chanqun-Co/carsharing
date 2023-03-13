package io.sharing.server.core.product.domain

/**
 * 관리자의 경우
 * - REGISTERED -> AVAILABLE
 * - REGISTERED -> REJECTED
 *
 * 호스트의 경우
 * - AVAILABLE -> UNAVAILABLE
 * - UNAVAILABLE -> AVAILABLE
 */
enum class ProductStatus {

    /** 등록 */
    REGISTERED,

    /** 거절 */
    REJECTED,

    /** 예약 가능 */
    AVAILABLE,

    /** 예약 불가능 */
    UNAVAILABLE
}
