package pl.edu.travelo.application.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.auth.core.CustomUserDetails;
import pl.edu.travelo.application.reservation.dto.ReservationCreateDto;
import pl.edu.travelo.application.reservation.dto.ReservationResponseDto;
import pl.edu.travelo.application.reservation.service.ReservationService;
import pl.edu.travelo.wrapper.ResponseWrapper;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseWrapper<ReservationResponseDto> create(
            @Valid @RequestBody ReservationCreateDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseWrapper.withStatus(HttpStatus.CREATED, reservationService.create(dto, userDetails.getId()));
    }
}
