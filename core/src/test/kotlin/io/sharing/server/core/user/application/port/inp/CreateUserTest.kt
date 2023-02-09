package io.sharing.server.core.user.application.port.inp

import io.sharing.server.core.support.assertion.assertTisExceptionThrows
import io.sharing.server.core.support.exception.TisError.USER_DUPLICATED_EMAIL
import io.sharing.server.core.support.test.BaseServiceTest
import io.sharing.server.core.user.application.port.outp.UserRepository
import io.sharing.server.core.user.domain.UserCreatedEvent
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.event.ApplicationEvents
import org.springframework.test.context.event.RecordApplicationEvents
import java.time.LocalDate

@RecordApplicationEvents
internal class CreateUserTest(
    private val createUser: CreateUser,
    private val userRepository: UserRepository
) : BaseServiceTest() {

    @Autowired
    lateinit var applicationEvents: ApplicationEvents

    @Test
    fun `유저 생성`() {
        val command = CreateUserCommand("woogie@gmail.com", "이름", "성", LocalDate.now())

        createUser.create(command)

        val foundUser = userRepository.findAll().first()

        assertThat(foundUser.email).isEqualTo(command.email)
        assertThat(foundUser.firstName).isEqualTo(command.firstName)
        assertThat(foundUser.lastName).isEqualTo(command.lastName)
        assertThat(foundUser.birthDay).isEqualTo(command.birthDay)

        assertThat(applicationEvents.stream(UserCreatedEvent::class.java).toList().first().user).isEqualTo(foundUser)
    }

    @Test
    fun `유저 생성 실패 - 이메일 중복`() {
        val email = "woogie@gmail.com"

        userRepository.save(createUser(email = email))

        assertTisExceptionThrows(USER_DUPLICATED_EMAIL) {
            createUser.create(CreateUserCommand(email, "이름", "성", LocalDate.now()))
        }
    }
}
