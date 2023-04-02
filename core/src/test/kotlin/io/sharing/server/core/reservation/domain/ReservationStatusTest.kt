package io.sharing.server.core.reservation.domain

import io.sharing.server.core.reservation.domain.ReservationStatus.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ReservationStatusTest {
    @Test
    fun `예약 상태 변경 TURE - PENDING to APPROVED`() {
        val reservationStatus = PENDING

        val isApproved = reservationStatus.isAbleToChangeTo(APPROVED)

        assertThat(isApproved).isTrue
    }

    @EnumSource(value = ReservationStatus::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    fun `상태 APPROVED로 변경 FALSE - 이전 상태가 PENDING 아닌 경우`(status: ReservationStatus) {
        val isApproved = status.isAbleToChangeTo(APPROVED)

        assertThat(isApproved).isFalse
    }

    @Test
    fun `예약 상태 변경 TURE - PENDING to DISAPPROVED`() {
        val reservationStatus = PENDING

        val isDisapproved = reservationStatus.isAbleToChangeTo(DISAPPROVED)

        assertThat(isDisapproved).isTrue
    }

    @EnumSource(value = ReservationStatus::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    fun `상태 DISAPPROVED로 변경 FALSE - 이전 상태가 PENDING 아닌 경우`(status: ReservationStatus) {
        val isDisapproved = status.isAbleToChangeTo(DISAPPROVED)

        assertThat(isDisapproved).isFalse
    }

    @Test
    fun `예약 상태 변경 TURE - APPROVED to CANCELLATION_REQUEST`() {
        val reservationStatus = APPROVED

        val isCancellationRequest = reservationStatus.isAbleToChangeTo(CANCELLATION_REQUEST)

        assertThat(isCancellationRequest).isTrue
    }

    @EnumSource(value = ReservationStatus::class, names = ["APPROVED"], mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    fun `상태 CANCELLATION_REQUEST로 변경 FALSE - 이전 상태가 APPROVED 아닌 경우`(status: ReservationStatus) {
        val isCancellationRequest = status.isAbleToChangeTo(CANCELLATION_REQUEST)

        assertThat(isCancellationRequest).isFalse
    }

    @Test
    fun `예약 상태 변경 TURE - CANCELLATION_REQUEST to CANCELED`() {
        val reservationStatus = CANCELLATION_REQUEST

        val isCanceled = reservationStatus.isAbleToChangeTo(CANCELED)

        assertThat(isCanceled).isTrue
    }

    @EnumSource(value = ReservationStatus::class, names = ["CANCELLATION_REQUEST"], mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    fun `상태 CANCELED로 변경 FALSE - 이전 상태가 CANCELLATION_REQUEST 아닌 경우`(status: ReservationStatus) {
        val isCanceled = status.isAbleToChangeTo(CANCELED)

        assertThat(isCanceled).isFalse
    }
}
