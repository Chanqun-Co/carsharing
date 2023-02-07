package io.sharing.server.core.user.domain

import java.time.LocalDate
import java.time.OffsetDateTime

class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDay: LocalDate,
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) {
    companion object {
        fun create(email: String, firstName: String, lastName: String, birthDay: LocalDate): User {
            return User(email, firstName, lastName, birthDay)
        }
    }
}
