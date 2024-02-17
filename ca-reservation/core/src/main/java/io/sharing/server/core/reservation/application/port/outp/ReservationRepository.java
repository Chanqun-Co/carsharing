package io.sharing.server.core.reservation.application.port.outp;

import io.sharing.server.core.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
