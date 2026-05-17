package pl.edu.travelo.application.trip.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.trip.dto.TripInfoDto;
import pl.edu.travelo.application.trip.dto.TripResponseDto;
import pl.edu.travelo.application.trip.service.TripService;
import pl.edu.travelo.wrapper.ResponseWrapper;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping
    public ResponseWrapper<Page<TripResponseDto>> findAll(Pageable pageable) {
        return ResponseWrapper.ok(tripService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseWrapper<TripInfoDto> findById(@PathVariable Long id) {
        return ResponseWrapper.ok(tripService.findById(id));
    }
}
