package pl.edu.travelo.trip.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.travelo.trip.dto.TripInfoDto;
import pl.edu.travelo.trip.dto.TripResponseDto;

public interface TripService {
    Page<TripResponseDto> findAll(Pageable pageable);

    TripInfoDto findById(Long id);
}
