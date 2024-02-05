package io.sharing.server.causer.emailauth.persistence

import io.sharing.server.causer.support.assertion.assertTisExceptionThrows
import io.sharing.server.causer.support.exception.TisError
import io.sharing.server.causer.support.exception.TooManyAuthCodeRetryException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class EmailAuthTest {

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

        emailAuth.generateAuthCode()

        assertThat(emailAuth.authCode.length).isEqualTo(6)
        assertThat(emailAuth.expiredTime).isAfter(LocalDateTime.now())
    }

    @Test
    fun `인증코드 검사 - 성공`() {
        val emailAuthCode = "123456"
        val emailAuth = createEmailAuth().apply {
            this.authCode = emailAuthCode
            this.authRetry = EmailAuth.MAX_RETRY_COUNT - 1
        }

        val result = emailAuth.checkEmailAuth(emailAuthCode)

        assertThat(result).isTrue
    }

    @Test
    fun `인증코드 검사 실패 - 코드 불일치`() {
        val emailAuthCode = "123456"
        val emailAuth = createEmailAuth().apply {
            this.authCode = emailAuthCode
        }

        val result = emailAuth.checkEmailAuth("123455")

        assertThat(result).isFalse
    }

    @Test
    fun `인증코드 검사 실패 - 시간이 초과 됐을 때`() {
        val emailAuth = createEmailAuth().apply {
            this.expiredTime = LocalDateTime.now().minusMinutes(1)
        }

        assertTisExceptionThrows(TisError.EMAIL_AUTH_TIME_OUT) {
            emailAuth.checkEmailAuth("123455")
        }
    }

    @Test
    fun `인증코드 검사 실패 - 인증횟수 초과`() {
        val emailAuth = createEmailAuth().apply {
            this.authCode = "123456"
            this.authRetry = EmailAuth.MAX_RETRY_COUNT
        }

        Assertions.assertThatThrownBy {
            emailAuth.checkEmailAuth("123455")
        }.isInstanceOf(TooManyAuthCodeRetryException::class.java)
    }
}

fun createEmailAuth(
    email: String = "ksh9241@naver.com",
    expiredTime: LocalDateTime = LocalDateTime.now().plusMinutes(EmailAuth.EXPIRED_MINUTE),
): EmailAuth {
    return EmailAuth.create(email = email).apply {
        this.expiredTime = expiredTime
    }
}
