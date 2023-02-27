package io.sharing.server.core.product.domain

import io.sharing.server.core.carmodel.domain.CarModel
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import io.sharing.server.core.user.domain.Region
import io.sharing.server.core.user.domain.User
import jakarta.persistence.*

/**
 * 상품
 */
@Entity
class Product(
    /** 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    /** 차량 모델 */
    @ManyToOne(fetch = FetchType.LAZY)
    val carModel: CarModel,

    /** 색상 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var color: ProductColor,

    /** 주행거리 */
    @Column(nullable = false)
    var distance: Int,

    /** 대여료 */
    @Column(nullable = false)
    var rentalFee: Int,

    /** 차량번호 */
    @Column(length = 50, nullable = false)
    var licensePlate: String,

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var status: ProductStatus = ProductStatus.REGISTERED,

    /** 지역 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    val region: Region,

    /** 설명 */
    @Column(columnDefinition = "TEXT")
    var description: String,

    /** 사진 */
    // TODO: Image 추가
    //    @OneToMany
    //    var images: List<Image>

) : BaseAggregateRoot<Product>()
