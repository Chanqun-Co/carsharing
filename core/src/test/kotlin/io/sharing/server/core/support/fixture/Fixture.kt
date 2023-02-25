package io.sharing.server.core.support.fixture

import io.sharing.server.core.carmodel.domain.*
import io.sharing.server.core.user.domain.User
import java.time.LocalDate

class Fixture {
    companion object {
        fun createUser(email: String = "woogie@gmail.com", firstName: String = "firstName", lastName: String = "lastName"): User {
            val birthDay = LocalDate.now()

            return User.create(email, firstName, lastName, birthDay)
        }

        fun createCarModel(color: Color = Color.BLACK, carName: CarName = CarName.GRANDEUR,
                           manufacturer: Manufacturer = Manufacturer.HYUNDAI, year: Int = 2022, fuel: Fuel = Fuel.GASOLINE): CarModel {

            return CarModel(color, carName, manufacturer, year, fuel)
        }
    }
}
