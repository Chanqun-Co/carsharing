package io.sharing.server.api.user.adapter.inp.web

import io.sharing.server.core.user.application.port.inp.CreateUser
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
// @RequestMapping("/users")
class UsersApi(
    private val createUser: CreateUser
) {

    @PostMapping("/users")
    fun create(@RequestBody @Valid req: UserCreateReq) {
        createUser.create(req.toCommand())
    }

    // API 쪽 구현이 안되어있어서 테스트용 2건 생성
    @PostMapping("/test")
    fun test() {
    }

    @PostMapping("/host/test")
    fun hostTest() {
    }
}
