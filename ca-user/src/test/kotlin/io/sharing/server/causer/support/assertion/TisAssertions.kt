package io.sharing.server.causer.support.assertion

import io.sharing.server.causer.support.exception.TisError
import io.sharing.server.causer.support.exception.TisException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

fun assertTisExceptionThrows(error: TisError, executable: () -> Unit) {
    val tisException = assertThrows<TisException>(executable)

    assertThat(tisException.error).isEqualTo(error)
}
