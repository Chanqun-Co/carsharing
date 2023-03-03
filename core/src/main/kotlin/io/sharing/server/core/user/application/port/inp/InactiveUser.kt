package io.sharing.server.core.user.application.port.inp

interface InactiveUser {

    /**
     * 유저를 상태를 INACTIVE로 변경한다.
     *
     * 유저가 존재하지 않는다면
     * @throws TisException()
     */
    fun inactive(id: Long)
}

// 단일 필드라서 써야할지 모르겠음.
// class FindUserCommand(
//    val id: Long
// )
