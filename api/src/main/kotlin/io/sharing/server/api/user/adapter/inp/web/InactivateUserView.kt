package io.sharing.server.api.user.adapter.inp.web

import io.sharing.server.core.user.application.port.inp.InactivateUserCommand

class InactivateUserView(
    val id: Long
) {
    fun toCommand(): InactivateUserCommand {
        return InactivateUserCommand(id)
    }
}
