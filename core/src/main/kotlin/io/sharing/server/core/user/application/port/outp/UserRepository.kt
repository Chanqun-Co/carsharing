package io.sharing.server.core.user.application.port.outp

import io.sharing.server.core.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
