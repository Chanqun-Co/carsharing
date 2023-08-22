package io.sharing.server.api.user.adapter.inp.web

import io.sharing.server.core.user.application.port.inp.CreateUser
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersApi(
    private val createUser: CreateUser
) {

    @PostMapping
    fun create(@RequestBody @Valid req: UserCreateReq) {
        createUser.create(req.toCommand())
    }
}
