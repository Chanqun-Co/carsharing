package io.sharing.server.core.emailauth.domain

import io.sharing.server.core.support.exception.TooManyAuthCodeRetryError.EXCEED_RETRY_COUNT
import io.sharing.server.core.support.exception.TooManyAuthCodeRetryException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class EmailAuthTest {

    @Test
    fun `이메일 인증 생성`() {
        val email = "ksh9241@naver.com"

        val createdEmailAuth = EmailAuth.create(email)

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
    fun `이메일 인증 횟수 증가 - 성공`() {
        val emailAuth = createEmailAuth()
        emailAuth.increaseAuthRetry()

        assertThat(emailAuth.authRetry).isEqualTo(1)
    }

    @Test
    fun `인증코드 검사 - 성공`() {
        val emailAuthCode = "123456"

        val emailAuth = createEmailAuth()
        emailAuth.authCode = emailAuthCode

        val result = emailAuth.checkEmailAuth(emailAuthCode)
        assertThat(result).isTrue()
    }

    @Test
    fun `인증코드 검사 실패 - 코드 불일치`() {
        val emailAuthCode = "123456"

        val emailAuth = createEmailAuth()
        emailAuth.authCode = emailAuthCode

        val result = emailAuth.checkEmailAuth("123455")
        assertThat(result).isFalse()
    }

    @Test
    fun `인증코드 검사 실패 - 인증횟수 초과`() {
        val emailAuth = createEmailAuth()
        emailAuth.authCode = "123456"

        assertThatThrownBy { for (i in 1..5) emailAuth.checkEmailAuth("123455") }
                .isInstanceOf(TooManyAuthCodeRetryException::class.java)
                .hasMessage(EXCEED_RETRY_COUNT.messageKey)
    }
}

fun createEmailAuth(
    email: String = "ksh9241@naver.com"
): EmailAuth {
    return EmailAuth.create(email = email)
}
