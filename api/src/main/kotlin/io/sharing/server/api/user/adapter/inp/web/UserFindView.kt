package io.sharing.server.api.user.adapter.inp.web

import io.sharing.server.core.user.application.port.inp.CreateUserCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

class UserCreateReq(
    @field:Email
    @field:Length(max = 50)
    val email: String,

    @field:NotBlank
    @field:Length(max = 50)
    val firstName: String,

    @field:NotBlank
    @field:Length(max = 50)
    val lastName: String,

    val birthDay: LocalDate
) {
    fun toCommand(): CreateUserCommand {
        return CreateUserCommand(email = email, firstName = firstName, lastName = lastName, birthDay)
    }
}
