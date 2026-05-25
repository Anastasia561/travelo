package pl.edu.travelo.application.trip;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.application.destination.DestinationMapper;
import pl.edu.travelo.application.trip.dto.TripReservationInfoDto;
import pl.edu.travelo.domain.model.Trip;
import pl.edu.travelo.application.trip.dto.TripInfoDto;
import pl.edu.travelo.application.trip.dto.TripResponseDto;
import pl.edu.travelo.application.vehicle.dto.VehicleBookingInfoResponseDto;
import pl.edu.travelo.application.vehicle.VehicleMapper;

@Component
public class TripMapper {
    private final VehicleMapper vehicleMapper;
    private final DestinationMapper destinationMapper;

    public TripMapper(VehicleMapper vehicleMapper, DestinationMapper destinationMapper) {
        this.vehicleMapper = vehicleMapper;
        this.destinationMapper = destinationMapper;
    }

    public TripResponseDto toDto(Trip trip) {
        if (trip == null) return null;

        return new TripResponseDto(
                trip.getId(),
                trip.getDepartureTime(),
                trip.getArrivalTime(),
                trip.getPrice(),
                trip.getAvailablePlaceCount(),
                trip.isFull(),
                trip.isCancelled(),
                trip.getStartCity().getName(),
                trip.getDestination().getCity().getName(),
                trip.getDestination().getName(),
                trip.getVehicle().getVehicleType());
    }

    public TripInfoDto toInfoDto(Trip trip) {
        if (trip == null) return null;
        VehicleBookingInfoResponseDto vehicleInfo = vehicleMapper.toBookingInfoDto(trip.getVehicle(), trip.getId());

        return new TripInfoDto(
                trip.getPrice(),
                vehicleInfo
        );
    }

    public TripReservationInfoDto toReservationInfoDto(Trip trip) {
        if (trip == null) return null;

        DestinationResponseDto destination = destinationMapper.toDto(trip.getDestination());
        return new TripReservationInfoDto(
                trip.getId(),
                trip.getDepartureTime(),
                trip.getArrivalTime(),
                trip.getStartCity().getName(),
                destination,
                trip.getVehicle().getVehicleType(),
                trip.getVehicle().getVehicleNumber()
        );
    }
}
