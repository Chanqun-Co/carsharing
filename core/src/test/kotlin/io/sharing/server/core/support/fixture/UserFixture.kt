package io.sharing.server.core.support.fixture

import io.sharing.server.core.user.domain.User
import java.time.LocalDate

class UserFixture {
    companion object {
        fun get(email: String = "woogie@gmail.com", firstName: String = "firstName", lastName: String = "lastName"): User {
            val birthDay = LocalDate.now()

            return User.create(email, firstName, lastName, birthDay)
        }
    }
}
