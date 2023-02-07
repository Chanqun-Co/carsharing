package io.sharing.server.core.user.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class UserTest {

    @Test
    fun `유저 생성`() {
        val email = "woogie@gmail.com"
        val firstName = "firstName"
        val lastName = "lastName"
        val birthDay = LocalDate.now()

        val user = User.create(email, firstName, lastName, birthDay)

        assertThat(user.email).isEqualTo(email)
        assertThat(user.firstName).isEqualTo(firstName)
        assertThat(user.lastName).isEqualTo(lastName)
        assertThat(user.birthDay).isEqualTo(birthDay)
        assertThat(user.createdAt).isNotNull
    }
}
