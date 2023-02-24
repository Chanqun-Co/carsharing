package io.sharing.server.core.product.domain

import io.sharing.server.core.support.jpa.BaseAggregateRoot
import io.sharing.server.core.user.domain.User
import jakarta.persistence.*

@Entity
class Product(

    /** 차량모델 */
//    @OneToOne(fetch = FetchType.LAZY)
//    val model: Model,

    /** 주행거리 */
    @Column(nullable = false)
    var distance: Int,

    /** 대여료 */
    @Column(nullable = false)
    var fee: Int,

    /** 차량번호 */
    @Column(length = 50, nullable = false)
    val licensePlate: String,

    /** 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    /** 상태정보: CREATED, DELETED */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var productStatus: ProductStatus,

    /** 지역 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    val location: Location,

    /** 설명 */
    @Column(columnDefinition = "TEXT")
    var description: String,

    /** 사진 */
//    @OneToMany
//    var images: List<Image>

) : BaseAggregateRoot<Product>()
