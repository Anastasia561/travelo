package pl.edu.travelo.application.reservation.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.customer.service.CustomerService;
import pl.edu.travelo.application.discount.service.DiscountService;
import pl.edu.travelo.application.reservation.dto.ReservationCreateDto;
import pl.edu.travelo.application.reservation.dto.ReservationResponseDto;
import pl.edu.travelo.application.reservation.mapper.ReservationMapper;
import pl.edu.travelo.application.reservation.repository.ReservationRepository;
import pl.edu.travelo.application.seat.service.SeatService;
import pl.edu.travelo.application.trip.service.TripService;
import pl.edu.travelo.domain.enums.ReservationStatus;
import pl.edu.travelo.domain.model.Customer;
import pl.edu.travelo.domain.model.Discount;
import pl.edu.travelo.domain.model.Reservation;
import pl.edu.travelo.domain.model.Seat;
import pl.edu.travelo.domain.model.Trip;

import java.time.LocalDateTime;
import java.util.Set;

@Service
class ReservationServiceImpl implements ReservationService {
    private final TripService tripService;
    private final CustomerService customerService;
    private final SeatService seatService;
    private final DiscountService discountService;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(TripService tripService, CustomerService customerService,
                                  SeatService seatService, DiscountService discountService,
                                  ReservationRepository reservationRepository,
                                  ReservationMapper reservationMapper) {
        this.tripService = tripService;
        this.customerService = customerService;
        this.seatService = seatService;
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

        Set<Seat> seats = seatService.findAllByIds(createDto.seatIds());

        boolean hasBookedSeat = seats.stream()
                .anyMatch(seat -> seat.isBooked(createDto.tripId()));

        if (hasBookedSeat) {
            throw new IllegalStateException("One or more selected seats are already booked.");
        }

        Customer customer = customerService.findById(customerId);
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() - createDto.loyaltyPoints());

        Reservation reservation;

        if (createDto.promeCode() == null || createDto.promeCode().isEmpty()) {
            Discount discount = discountService.findByPromeCode(createDto.promeCode());
            reservation = new Reservation(LocalDateTime.now(), ReservationStatus.PENDING,
                    trip, customer, seats, discount);
        } else {
            reservation = new Reservation(LocalDateTime.now(), ReservationStatus.PENDING,
                    trip, customer, seats);
        }

        reservationRepository.save(reservation);

        return reservationMapper.toResponseDto(reservation, reservation.getTotalPrice(createDto.loyaltyPoints()));
    }
}
