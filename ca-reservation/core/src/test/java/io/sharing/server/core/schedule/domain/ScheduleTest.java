package io.sharing.server.core.schedule.domain;

import io.sharing.server.core.reservation.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.OffsetDateTime;

public class ScheduleTest {
    private final long MINIMUM_RESERVABLE_HOUR = 2L;
    private final long MINIMUM_BLOCKED_HOUR = 1L;


//    @Test
//    @DisplayName("스케줄 생성 - RESERVED")
//    void addSchedule_reserved() {
//        // Product 생성해야 됨.
//
//        Reservation reservation = new Reservation();
//        OffsetDateTime startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0);
//        OffsetDateTime endTime = startTime.plusHours(MINIMUM_RESERVABLE_HOUR);
//    }
//
//    @Test
//    @DisplayName("스케줄 생성 - BLOCKED")
//    void addSchedule_blocked() {
//        OffsetDateTime startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0);
//        OffsetDateTime endTime = startTime.plusHours(MINIMUM_RESERVABLE_HOUR);
//    }
//
//    @ParameterizedTest
//    @CsvSource({"2023-03-15T12:01:00.001Z", "2023-03-15T12:00:01.000Z", "2023-03-15T12:10:00.001Z"})
//    @DisplayName("스케줄 생성 실패 - startTime 정시가 아닐 경우")
//    void addSchedule_fail(String startTime) {
//        Reservation reservation = new Reservation();
//        OffsetDateTime endTime = OffsetDateTime(startTime).plusHours(MINIMUM_RESERVABLE_HOUR + 1).withMinute(0).withSecond(0).withNano(0);
//    }
//
//    @ParameterizedTest
//    @CsvSource({"2023-03-15T12:01:00.001Z", "2023-03-15T12:00:01.000Z", "2023-03-15T12:10:00.001Z"})
//    @DisplayName("스케줄 생성 실패 - endTime 정시가 아닐 경우")
//    void addSchedule_fail(String endTime) {
//        Reservation reservation = new Reservation();
//        OffsetDateTime startTime = endTime.minusHours(MINIMUM_RESERVABLE_HOUR + 1).withMinute(0).withSecond(0).withNano(0);
//    }
//
//    @Test
//    @DisplayName("스케줄 생성 실패 - RESERVED startTime, endTime 사이의 시간차가 2시간 이전 일 때")
//    void addSchedule_fail() {
//        Reservation reservation = new Reservation();
//        OffsetDateTime startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0);
//        OffsetDateTime endTime = startTime.plusHours(MINIMUM_RESERVABLE_HOUR).minusHours(1);
//
//    }
//
//    @Test
//    @DisplayName("스케줄 생성 실패 - startTime, endTime 사이의 시간차가 1시간 이내일 경우")
//    void addSchedule_fail() {
//        OffsetDateTime startTime = OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0);
//        OffsetDateTime endTime = startTime.plusHours(MINIMUM_BLOCKED_HOUR).minusHours(1);
//    }


}
