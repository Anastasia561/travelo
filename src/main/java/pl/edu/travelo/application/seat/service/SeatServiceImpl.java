package pl.edu.travelo.application.seat.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.travelo.domain.model.Seat;
import pl.edu.travelo.application.seat.repository.SeatRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Transactional
    @Override
    public void lockSeat(long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));

        seat.setBooked(true);
    }

    @Override
    public Set<Seat> findAllByIds(Set<Long> ids) {
        List<Seat> foundSeatsList = seatRepository.findAllById(ids);

        if (foundSeatsList.size() != ids.size()) {
            Set<Long> foundIds = foundSeatsList.stream()
                    .map(Seat::getId)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = new HashSet<>(ids);
            missingIds.removeAll(foundIds);

            throw new EntityNotFoundException("Could not find seats with IDs: " + missingIds);
        }

        return new HashSet<>(foundSeatsList);
    }
}
