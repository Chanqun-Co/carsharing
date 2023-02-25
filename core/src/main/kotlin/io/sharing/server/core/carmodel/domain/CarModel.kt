package io.sharing.server.core.carmodel.domain

import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class CarModel(

    /** 차량색상 */
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = true)
    val color: Color,

    /** 차량모델명 */
    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = true)
    val name: CarName,

    /** 차량제조사 */
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = true)
    val manufacturer: Manufacturer,

    /** 연식 */
    @Column(length = 4, nullable = true)
    val year: Int,

    /** 연료 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = true)
    val fuel: Fuel

) : BaseAggregateRoot<CarModel>()
