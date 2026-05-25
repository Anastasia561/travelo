package pl.edu.travelo.application.trip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.travelo.application.trip.dto.TripInfoDto;
import pl.edu.travelo.application.trip.dto.TripResponseDto;
import pl.edu.travelo.domain.model.Trip;

public interface TripService {
    Page<TripResponseDto> findAll(Pageable pageable);

    TripInfoDto findById(Long id);

    Trip findTripById(Long id);
}
