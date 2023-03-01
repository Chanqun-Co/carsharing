package io.sharing.server.core.product.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * 상품 이미지
 */
@Embeddable
class ProductImage(

    /** 이미지 url 주소 */
    @Column(nullable = false)
    var url: String,
)
