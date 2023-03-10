package io.sharing.server.core.user.domain

import io.sharing.server.core.user.domain.Region.*
import io.sharing.server.core.user.domain.UserStatus.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
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
        assertThat(user.status).isEqualTo(ACTIVE)
        assertThat(user.region).isNull()
        assertThat(user.createdAt).isNotNull
    }

    @Test
    fun `유저 생성 실패 - 생일은 미래가 될 수 없다`() {
        assertThatIllegalArgumentException().isThrownBy {
            createUser(birthDay = LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `유저 비활성화`() {
        val user = createUser().apply {
            this.status = ACTIVE
        }

        user.inactivate()

        assertThat(user.status).isEqualTo(INACTIVE)
    }

    @Test
    fun `유저 비활성화 실패 - 이미 비활성화 된 유저`() {
        val user = createUser().apply {
            this.status = INACTIVE
        }

        assertThatIllegalArgumentException().isThrownBy {
            user.inactivate()
        }
    }

    @Test
    fun `지역 등록`() {
        val user = createUser()

        user.registerRegion(DANGSAN)

        assertThat(user.region).isEqualTo(DANGSAN)
    }
}

fun createUser(
    email: String = "woogie@gmail.com",
    firstName: String = "firstName",
    lastName: String = "lastName",
    birthDay: LocalDate = LocalDate.now(),
): User {
    return User.create(email, firstName, lastName, birthDay)
}
