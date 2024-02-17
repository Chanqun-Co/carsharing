package io.sharing.server.core.reservation.application.port.inp;

import lombok.Data;
import org.springframework.stereotype.Service;

// UseCase
@Service
public interface CreateReservation {
    /**
     * 예약을 등록한다.
     * */
    void create(CreateReservationCommand command);
}

@Data
class CreateReservationCommand {
    long host;
    long guest;
}