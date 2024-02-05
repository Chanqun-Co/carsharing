package io.sharing.server.causer.support.exception

class TisException(val error: TisError = TisError.UNKNOWN) : RuntimeException(error.messageKey)

enum class TisError(
    val messageKey: String? = null
) {
    UNKNOWN("error.unknown"),

    USER_DUPLICATED_EMAIL("error.user.duplicated.email"),

    EMAIL_AUTH_TIME_OUT("error.email.auth.time.out"),
}
