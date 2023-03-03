package io.sharing.server.core.user.application.port.inp

import io.sharing.server.core.support.test.BaseServiceTest
import io.sharing.server.core.user.application.port.outp.UserRepository
import io.sharing.server.core.user.domain.UserStatus.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.event.RecordApplicationEvents
import java.time.LocalDate

@RecordApplicationEvents
internal class InactiveUserTest(
    private val createUser: CreateUser,
    private val inactiveUser: InactiveUser,
    private val userRepository: UserRepository
) : BaseServiceTest() {

    @BeforeEach
    fun init() {
        val command = CreateUserCommand("woogie@gmail.com", "이름", "성", LocalDate.now())

        createUser.create(command)
    }

    @Test
    fun `유저 삭제`() {
        val foundUser = userRepository.findAll().first()

        foundUser.id?.let { inactiveUser.inactive(it) }

        assertThat(foundUser.status).isEqualTo(INACTIVE)
    }
}
