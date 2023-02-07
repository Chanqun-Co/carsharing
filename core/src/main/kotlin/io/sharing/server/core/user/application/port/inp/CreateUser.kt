package io.sharing.server.core.user.application.port.inp

import java.time.LocalDate

interface CreateUser {

    /**
     * 유저를 등록한다.
     *
     * 중복된 이메일로 등록 시도하면
     * @throws TisException(USER_DUPLICATED_EMAIL)
     */
    fun create(command: CreateUserCommand)
}

class CreateUserCommand(
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDay: LocalDate
)
