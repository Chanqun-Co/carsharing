package io.sharing.server.core.emailauth.domain

import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
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
    val authRetry: Int = 0,

    /** 인증 코드 - 6자리 숫자 */
    @Column(length = 6, nullable = false)
    val authCode: String = "",

    /** 인증 만료 시간 */
    val expiredTime: LocalDateTime = LocalDateTime.now(),
) : BaseAggregateRoot<EmailAuth>()
