package io.sharing.server.core.carmodel.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class CarModelTest {

    @Test
    fun `차량 모델 생성`() {
        val name = CarName.GRANDEUR
        val manufacturer = Manufacturer.HYUNDAI
        val year = 2022
        val fuel = Fuel.GASOLINE

        val carModel = CarModel(name = name, manufacturer = manufacturer, year = year, fuel = fuel)

        assertThat(carModel.name).isEqualTo(name)
        assertThat(carModel.manufacturer).isEqualTo(manufacturer)
        assertThat(carModel.year).isEqualTo(year)
        assertThat(carModel.fuel).isEqualTo(fuel)
    }
}

fun createCarModel(
    carName: CarName = CarName.GRANDEUR,
    manufacturer: Manufacturer = Manufacturer.HYUNDAI, year: Int = 2022, fuel: Fuel = Fuel.GASOLINE
): CarModel {
    return CarModel(carName, manufacturer, year, fuel)
}
