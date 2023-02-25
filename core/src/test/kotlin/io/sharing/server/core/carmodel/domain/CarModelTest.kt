package io.sharing.server.core.carmodel.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CarModelTest {

    @Test
    fun `차량 모델 생성`() {
        val color = Color.BLACK
        val name = CarName.GRANDEUR
        val manufacturer = Manufacturer.HYUNDAI
        val year = 2022
        val fuel = Fuel.GASOLINE

        val carModel = CarModel(color = color, name = name, manufacturer = manufacturer, year = year, fuel = fuel)

        Assertions.assertThat(carModel.color).isEqualTo(color)
        Assertions.assertThat(carModel.name).isEqualTo(name)
        Assertions.assertThat(carModel.manufacturer).isEqualTo(manufacturer)
        Assertions.assertThat(carModel.year).isEqualTo(year)
        Assertions.assertThat(carModel.fuel).isEqualTo(fuel)
    }
}
