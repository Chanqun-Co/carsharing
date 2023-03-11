package io.sharing.server.core.user.application.port.inp

import io.sharing.server.core.support.test.BaseServiceTest
import io.sharing.server.core.user.application.port.outp.UserRepository
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.UserStatus.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class InactivateUserTest(
    private val inactivateUser: InactivateUser,
    private val userRepository: UserRepository
) : BaseServiceTest() {

    @Test
    fun `유저 비활성화`() {
        val user = User.create("woogie@gmail.com", "이름", "성", LocalDate.now())
        userRepository.save(user)

        val inactivateUserCommand = InactivateUserCommand(user.id!!)
        inactivateUser.inactivate(inactivateUserCommand)

        assertThat(user.status).isEqualTo(INACTIVE)
    }
}
