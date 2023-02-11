package io.sharing.server.core.emailauth.domain

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

    /** 인증 시도 횟수 - 5회 */
    @Column(columnDefinition = "TINYINT", nullable = false)
    var authRetry: Int = 0,

    /** 인증 코드 - 6자리 숫자 */
    @Column(length = 6, nullable = false)
    var authCode: String = "",

    /** 인증 만료 시간 - 인증코드 발급 후 5분 */
    var expiredTime: LocalDateTime = LocalDateTime.now(),
) : BaseAggregateRoot<EmailAuth>() {
    fun generateAuthCode() {
        val from = 100000
        val to = 999999
        authCode = (SecureRandom().nextInt(to - from) + from).toString()
        expiredTime = LocalDateTime.now().plusMinutes(5)
    }

    fun checkRetryCount() {
        if (authRetry >= MAX_RETRY_COUNT) {
            throw Exception("이메일 인증 시도 횟수 초과")
        }
    }

    companion object {
        /** 최대 인증 시도 횟수 */
        const val MAX_RETRY_COUNT = 5

        fun create(email: String): EmailAuth {
            return EmailAuth(email)
        }
    }
}
