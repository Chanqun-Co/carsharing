package io.sharing.server.core.emailauth.domain

import io.sharing.server.core.support.exception.TooManyAuthCodeRetryError.EXCEED_RETRY_COUNT
import io.sharing.server.core.support.exception.TooManyAuthCodeRetryException
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.security.SecureRandom
import java.time.LocalDateTime

/**
 * 이메일 인증
 */
@Entity
@Table(
    name = "email_auths",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_email_auth_email", columnNames = ["email"])
    ]
)
class EmailAuth(

    /** 이메일 */
    @Column(length = 50, nullable = false)
    val email: String,

    /** 인증 시도 횟수 */
    @Column(columnDefinition = "TINYINT", nullable = false)
    var authRetry: Int = 0,

    /** 인증 코드 */
    @Column(length = 6, nullable = false)
    var authCode: String = "",

    /** 인증 만료 시간 */
    var expiredTime: LocalDateTime = LocalDateTime.now(),
) : BaseAggregateRoot<EmailAuth>() {
    fun generateAuthCode() {
        authCode = String.format("%06d", secureRandom.nextInt(1_000_000))
        expiredTime = LocalDateTime.now().plusMinutes(5)
        authRetry = 0
    }

    fun increaseAuthRetry() {
        authRetry++
        validateRetryCount()
    }

    private fun validateRetryCount() {
        if (authRetry >= MAX_RETRY_COUNT) {
            throw TooManyAuthCodeRetryException(EXCEED_RETRY_COUNT)
        }
    }

    fun checkEmailAuth(emailAuthCode: String): Boolean {
        return if (authCode == emailAuthCode) {
            true
        } else {
            increaseAuthRetry()
            false
        }
    }

    companion object {
        /** 최대 인증 시도 횟수 */
        const val MAX_RETRY_COUNT = 5
        private val secureRandom = SecureRandom()

        fun create(email: String): EmailAuth {
            return EmailAuth(email)
        }
    }
}
