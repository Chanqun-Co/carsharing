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
    var status: ProductStatus = ProductStatus.SCREENING,

    /** 지역 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    val region: Region,

    /** 설명 */
    @Column(columnDefinition = "TEXT")
    var description: String,

    /** 이미지 */
    @ElementCollection
    @CollectionTable(name = "product_img", joinColumns = [JoinColumn(name = "product_id")])
    var images: MutableList<String> = mutableListOf(),
) : BaseAggregateRoot<Product>() {
    fun updateImages(images: MutableList<String>) {
        this.images = images
    }

    fun screen() {
        require(this.status == ProductStatus.DISAPPROVED)

        this.status = ProductStatus.SCREENING
    }

    fun disapprove() {
        require(this.status == ProductStatus.SCREENING)

        this.status = ProductStatus.DISAPPROVED
    }

    fun cancel() {
        require(this.status == ProductStatus.SCREENING || this.status == ProductStatus.DISAPPROVED)

        this.status = ProductStatus.CANCELED
    }

    fun makeAvailable() {
        require(this.status == ProductStatus.SCREENING || this.status == ProductStatus.UNAVAILABLE)

        this.status = ProductStatus.AVAILABLE
    }

    fun makeUnavailable() {
        require(this.status == ProductStatus.AVAILABLE)

        this.status = ProductStatus.UNAVAILABLE
    }
}
