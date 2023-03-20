package io.sharing.server.core.schedule.domain
/**
 * 스케줄 타입
 * 스케줄을 생성한 주체
 * RESERVATION: 예약에 의해서 스케줄이 생성
 *
 * HOST: 호스트가 예약이 불가능한 날짜를 지정한 상태 (호스트에 의해서 스케줄 생성)
 */
enum class ScheduleType {
    /** 예약됨 */
    RESERVATION,

    /** 예약 불가능한 */
    HOST
}
