package io.sharing.server.core.product.domain

import io.sharing.server.core.support.jpa.BaseAggregateRoot
import io.sharing.server.core.support.jpa.Status
import io.sharing.server.core.user.domain.Region
import io.sharing.server.core.user.domain.User
import jakarta.persistence.*

@Entity
class Product(

    /** 차량모델 */
    // TODO: 차량모델
    //    @OneToOne(fetch = FetchType.LAZY)
    //    val model: Model,

    /** 주행거리 */
    @Column(nullable = false)
    var distance: Int,

    /** 대여료 */
    @Column(nullable = false)
    var rentalFee: Int,

    /** 차량번호 */
    @Column(length = 50, nullable = false)
    var licensePlate: String,

    /** 유저 */
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    /** 상태정보 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var status: Status = Status.ACTIVE,

    /** 지역 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    val region: Region,

    /** 상품설명:  */
    @Column(columnDefinition = "TEXT")
    var description: String,

    /** 사진 */
    // TODO: Image 추가
    //    @OneToMany
    //    var images: List<Image>

) : BaseAggregateRoot<Product>()
