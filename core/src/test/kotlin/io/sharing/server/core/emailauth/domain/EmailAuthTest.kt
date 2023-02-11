package io.sharing.server.core.emailauth.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class EmailAuthTest {

    @Test
    fun `이메일 인증 생성`() {
        val email = "ksh9241@naver.com"
        val createdEmailAuth = EmailAuth.create(email = email)

        assertThat(createdEmailAuth.email).isEqualTo(email)
        assertThat(createdEmailAuth.expiredTime).isNotNull
        assertThat(createdEmailAuth.authRetry).isEqualTo(0)
        assertThat(createdEmailAuth.authCode).isEqualTo("")
    }

    @Test
    fun `이메일 인증 코드 생성`() {
        val emailAuth = createEmailAuth()
        val expiredTime = LocalDateTime.now().plusMinutes(5).withNano(0)
        emailAuth.generateAuthCode()

        assertThat(emailAuth.authCode.length).isEqualTo(6)
        assertThat(emailAuth.expiredTime.withNano(0)).isEqualTo(expiredTime)
    }

    @Test
    fun `이메일 인증 횟수 체크 - 예외처리`() {
        val MAX_RETRY_COUNT = 5
        val emailAuth = createEmailAuth()
        emailAuth.authRetry = MAX_RETRY_COUNT

        Assertions.assertThatException().isThrownBy {
            emailAuth.checkRetryCount()
        }
    }
}

fun createEmailAuth(
    email: String = "ksh9241@naver.com"
): EmailAuth {
    return EmailAuth.create(email = email)
}
