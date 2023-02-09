package io.sharing.server.core.user.domain

import io.sharing.server.core.support.jpa.BaseAggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate
import java.time.OffsetDateTime

/**
 * 유저
 */
@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_user_email", columnNames = ["email"])
    ]
)
class User(

    /** 이메일 */
    @Column(length = 50, nullable = false)
    val email: String,

    /** 이름 */
    @Column(length = 50, nullable = false)
    val firstName: String,

    /** 성 */
    @Column(length = 50, nullable = false)
    val lastName: String,

    /** 생일 */
    val birthDay: LocalDate,

    /** 생성일시 */
    val createdAt: OffsetDateTime = OffsetDateTime.now()
) : BaseAggregateRoot<User>() {
    companion object {
        fun create(email: String, firstName: String, lastName: String, birthDay: LocalDate): User {
            require(birthDay <= LocalDate.now())

            return User(email, firstName, lastName, birthDay).apply {
                this.registerEvent(UserCreatedEvent(this))
            }
        }
    }
}
