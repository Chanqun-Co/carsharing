package io.sharing.server.core.user.application.port.inp

import io.sharing.server.core.support.test.BaseServiceTest
import io.sharing.server.core.user.application.port.outp.UserRepository
import io.sharing.server.core.user.domain.UserStatus.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

internal class InactivateUserTest(
    private val createUser: CreateUser,
    private val inactivateUser: InactivateUser,
    private val userRepository: UserRepository
) : BaseServiceTest() {

    @BeforeEach
    fun init() {
        val command = CreateUserCommand("woogie@gmail.com", "이름", "성", LocalDate.now())

        createUser.create(command)
    }

    @Test
    fun `유저 비활성화`() {
        val foundUser = userRepository.findAll().first()
        val inactivateUserCommand = foundUser.id?.let { InactivateUserCommand(it) }

        inactivateUserCommand?.let { inactivateUser.inactive(it) }

        assertThat(foundUser.status).isEqualTo(INACTIVE)
    }

    @Test
    fun `유저 비활성화 실패 - 이미 비활성화 된 유저`() {
        val foundUser = userRepository.findAll().first()

        foundUser.apply {
            this.status = INACTIVE
        }

        assertThrows<IllegalArgumentException> {
            foundUser.inactivate()
        }
    }
}
