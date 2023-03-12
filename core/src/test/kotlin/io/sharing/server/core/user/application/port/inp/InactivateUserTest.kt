package io.sharing.server.core.user.application.port.inp

import io.sharing.server.core.support.test.BaseServiceTest
import io.sharing.server.core.user.application.port.outp.UserRepository
import io.sharing.server.core.user.domain.UserStatus.*
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class InactivateUserTest(
    private val inactivateUser: InactivateUser,
    private val userRepository: UserRepository
) : BaseServiceTest() {

    @Test
    fun `유저 비활성화`() {
        val user = userRepository.save(createUser().apply {
            this.status = ACTIVE
        })

        inactivateUser.inactivate(InactivateUserCommand(user.id!!))

        assertThat(user.status).isEqualTo(INACTIVE)
    }
}
