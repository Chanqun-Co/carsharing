package io.sharing.server.core.support.exception

class TooManyAuthCodeRetryException(val error: TooManyAuthCodeRetryError = TooManyAuthCodeRetryError.UNKNOWN) : Exception(error.messageKey)

enum class TooManyAuthCodeRetryError(
        val messageKey: String? = null
) {
    UNKNOWN("error.unknown"),

    EXCEED_RETRY_COUNT("error.email_auth.exceed.count")
}
