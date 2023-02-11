package io.sharing.server.core.emailauth.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EmailAuthTest {

    @Test
    fun `이메일 인증 생성`() {
        val email = "ksh9241@naver.com"
        val createdEmailAuth = EmailAuth(email = email)

        assertThat(createdEmailAuth.email).isEqualTo(email)
        assertThat(createdEmailAuth.expiredTime).isNotNull
        assertThat(createdEmailAuth.authRetry).isEqualTo(0)
        assertThat(createdEmailAuth.authCode).isEqualTo("")
    }
}
