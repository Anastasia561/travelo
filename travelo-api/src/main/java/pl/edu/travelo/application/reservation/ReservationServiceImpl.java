package pl.edu.travelo.application.reservation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.customer.CustomerService;
import pl.edu.travelo.application.discount.DiscountService;
import pl.edu.travelo.application.reservation.dto.ReservationCreateDto;
import pl.edu.travelo.application.reservation.dto.ReservationResponseDto;
import pl.edu.travelo.application.trip.TripService;
import pl.edu.travelo.domain.enums.ReservationStatus;
import pl.edu.travelo.domain.model.Customer;
import pl.edu.travelo.domain.model.Discount;
import pl.edu.travelo.domain.model.Reservation;
import pl.edu.travelo.domain.model.Seat;
import pl.edu.travelo.domain.model.Trip;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class ReservationServiceImpl implements ReservationService {
    private final TripService tripService;
    private final CustomerService customerService;
    private final DiscountService discountService;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(TripService tripService, CustomerService customerService,
                                  DiscountService discountService,
                                  ReservationRepository reservationRepository,
                                  ReservationMapper reservationMapper) {
        this.tripService = tripService;
        this.customerService = customerService;
        this.discountService = discountService;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    @Override
    public ReservationResponseDto create(ReservationCreateDto createDto, long customerId) {
        Trip trip = tripService.findTripById(createDto.tripId());
        if (trip.isFull() || trip.isCancelled()) {
            throw new IllegalStateException("Trip is not available for booking");
        }

        Set<Seat> selectedSeats = validateSeats(createDto.seatIds(), trip);

        Customer customer = customerService.findById(customerId);
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() - createDto.loyaltyPoints());

        Reservation reservation;
        if (createDto.promeCode() != null && !createDto.promeCode().isBlank()) {
            Discount discount = discountService.findByPromeCode(createDto.promeCode());
            reservation = new Reservation(LocalDateTime.now(), ReservationStatus.PENDING,
                    trip, customer, selectedSeats, discount, createDto.loyaltyPoints());
        } else {
            reservation = new Reservation(LocalDateTime.now(), ReservationStatus.PENDING,
                    trip, customer, selectedSeats, createDto.loyaltyPoints()
            );
        }

        reservationRepository.save(reservation);
        return reservationMapper.toResponseDto(reservation);
    }

    private Set<Seat> validateSeats(Set<Long> seatIds, Trip trip) {
        Set<Seat> selectedSeats = trip.getVehicle().getSeats().stream()
                .filter(seat -> seatIds.contains(seat.getId()))
                .collect(Collectors.toSet());

        if (selectedSeats.size() != seatIds.size()) {
            Set<Long> foundIds = selectedSeats.stream().map(Seat::getId).collect(Collectors.toSet());
            Set<Long> missingIds = new HashSet<>(seatIds);
            missingIds.removeAll(foundIds);
            throw new EntityNotFoundException("Seats not found or do not belong to this trip's vehicle: " + missingIds);
        }

        boolean hasBookedSeat = selectedSeats.stream()
                .anyMatch(seat -> seat.isBooked(trip.getId()));

        if (hasBookedSeat) {
            throw new IllegalStateException("One or more selected seats are already booked for this trip.");
        }
        return selectedSeats;
    }
}
