package io.sharing.server.api.reservation.adapter.inp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationApi {

//    private final CreateReservation createReservation;

    @PostMapping("/create")
    public void create(@RequestBody ReservationReq req) {

    }
}
