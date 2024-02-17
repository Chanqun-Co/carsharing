package io.sharing.server.core.reservation.domain;

import java.util.List;

/**
 * 호스트의 경우
 * PENDING -> APPROVED
 *         -> DISAPPROVED
 *
 * REQUEST_CANCEL -> CANCELED
 *
 * 게스트의 경우
 * APPROVED -> REQUEST_CANCEL
 */
public enum ReservationStatus {
    REJECTED(),

    CANCELED(),

    REQUEST_CANCEL(),

    APPROVED(),

    PENDING();

//    private List<ReservationStatus> nextStatuses;

    boolean canChangeTo(ReservationStatus status) {
        boolean result = true;
        switch (this) {
            case REQUEST_CANCEL : if (status == CANCELED) result = false;
                break;
            case APPROVED : if (status == REQUEST_CANCEL) result = false;
                break;
            case PENDING : if (status == APPROVED || status == REJECTED) result = false;
                break;
        }

        return result;
    }

}
