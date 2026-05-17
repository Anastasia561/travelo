package pl.edu.travelo.application.trip.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.travelo.application.trip.dto.TripInfoDto;
import pl.edu.travelo.application.trip.dto.TripResponseDto;

public interface TripService {
    Page<TripResponseDto> findAll(Pageable pageable);

    TripInfoDto findById(Long id);
}
