package io.sharing.server.core.product.domain

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
