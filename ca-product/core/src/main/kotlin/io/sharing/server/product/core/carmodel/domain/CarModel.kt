package io.sharing.server.product.core.carmodel.domain

import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class CarModel(

    /** 이름 */
    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    var name: CarName,

    /** 제조사 */
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    var manufacturer: Manufacturer,

    /** 연식 */
    @Column(nullable = false)
    var year: Int,

    /** 연료 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var fuel: Fuel
) : BaseAggregateRoot<CarModel>()