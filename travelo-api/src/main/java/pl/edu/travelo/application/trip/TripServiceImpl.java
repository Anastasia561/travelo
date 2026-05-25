package pl.edu.travelo.application.trip;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.trip.dto.TripInfoDto;
import pl.edu.travelo.application.trip.dto.TripResponseDto;
import pl.edu.travelo.domain.model.Trip;

@Service
class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    public Page<TripResponseDto> findAll(Pageable pageable) {
        return tripRepository.findAllWithDetails(pageable).map(tripMapper::toDto);
    }

    @Override
    public TripInfoDto findById(Long id) {
        return tripRepository.findByIdWithVehicleSeatsAndReservations(id)
                .map(tripMapper::toInfoDto)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
    }

    @Override
    public Trip findTripById(Long id) {
        return tripRepository.findByIdWithVehicleSeatsAndReservations(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
    }
}
