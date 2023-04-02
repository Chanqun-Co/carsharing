package io.sharing.server.core.user.domain

/**
 * 권한
 * */
enum class Role {
    /** 회원가입 된 상태 */
    GUEST,

    /** 이메일 인증이 완료된 상태 */
    HOST
}
