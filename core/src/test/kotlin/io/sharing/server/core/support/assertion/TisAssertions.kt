package io.sharing.server.core.support.assertion

import io.sharing.server.core.support.exception.TisError
import io.sharing.server.core.support.exception.TisException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

fun assertTisExceptionThrows(error: TisError, executable: () -> Unit) {
    val tisException = assertThrows<TisException>(executable)

    assertThat(tisException.error).isEqualTo(error)
}
