package io.sharing.server.core.schedule.domain
/**
 * 스케줄 타입
 * 상품에 대한 계획된 일정
 * RESERVED: 예약에 의해서 스케줄이 생성
 *
 * BLOCKED: 호스트가 예약이 불가능한 날짜를 지정한 상태 (호스트에 의해서 스케줄 생성)
 */
enum class ScheduleType {
    /** 예약됨 */
    RESERVED,

    /** 예약 불가능한 */
    BLOCKED
}
