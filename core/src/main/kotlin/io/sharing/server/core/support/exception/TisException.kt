package io.sharing.server.core.support.exception

class TisException(val error: TisError = TisError.UNKNOWN) : RuntimeException(error.messageKey)

enum class TisError(
    val code: Int,
    val messageKey: String? = null
) {
    UNKNOWN(1, "error.unknown"),

    USER_DUPLICATED_EMAIL(101, "error.user.duplicated.email")
}
