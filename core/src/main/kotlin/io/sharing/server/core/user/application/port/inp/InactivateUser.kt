package io.sharing.server.core.user.application.port.inp

interface InactivateUser {

    /**
     * 유저를 상태를 INACTIVE로 변경한다.
     */
    fun inactivate(command: InactivateUserCommand)
}

 class InactivateUserCommand(
    val userId: Long
 )
