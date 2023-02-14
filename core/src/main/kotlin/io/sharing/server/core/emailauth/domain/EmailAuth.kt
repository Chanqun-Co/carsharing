package io.sharing.server.core.emailauth.domain

import io.sharing.server.core.support.exception.TisError.*
import io.sharing.server.core.support.exception.TisException
import io.sharing.server.core.support.exception.TooManyAuthCodeRetryException
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime
import kotlin.random.Random

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
        this.authCode = String.format("%06d", Random.nextInt(1_000_000))
        this.expiredTime = LocalDateTime.now().plusMinutes(EXPIRED_MINUTE)
        this.authRetry = 0
    }

    fun checkEmailAuth(authCode: String): Boolean {
        if (this.expiredTime < LocalDateTime.now()) {
            throw TisException(EMAIL_AUTH_TIME_OUT)
        }

        if (this.authRetry++ >= MAX_RETRY_COUNT) {
            throw TooManyAuthCodeRetryException()
        }

        return this.authCode == authCode
    }

    companion object {
        const val MAX_RETRY_COUNT = 5
        const val EXPIRED_MINUTE = 5L

        fun create(email: String): EmailAuth {
            return EmailAuth(email)
        }
    }
}
