package pl.edu.travelo.application.vehicle.mapper;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.application.seat.mapper.SeatMapper;
import pl.edu.travelo.domain.model.Destination;
import pl.edu.travelo.domain.model.Seat;
import pl.edu.travelo.domain.model.Trip;
import pl.edu.travelo.domain.model.Vehicle;
import pl.edu.travelo.application.seat.dto.SeatDto;
import pl.edu.travelo.application.vehicle.dto.VehicleBookingInfoResponseDto;
import pl.edu.travelo.application.vehicle.dto.VehicleInfoResponseDto;
import pl.edu.travelo.application.vehicle.dto.VehicleResponseDto;

import java.util.HashSet;
import java.util.Set;

@Component
public class VehicleMapper {
    private final SeatMapper seatMapper;

    public VehicleMapper(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    public VehicleResponseDto toDto(Vehicle vehicle) {
        if (vehicle == null) return null;

        return new VehicleResponseDto(
                vehicle.getId(),
                vehicle.getVehicleNumber(),
                vehicle.getVehicleType(),
                vehicle.getMaxRow(),
                vehicle.getRowWidth());
    }

    public VehicleInfoResponseDto toInfoDto(Vehicle vehicle) {
        if (vehicle == null) return null;
        Set<DestinationResponseDto> destinations = new HashSet<>();

        for (Trip trip : vehicle.getTrips()) {
            Destination destination = trip.getDestination();

            DestinationResponseDto destinationDto = new DestinationResponseDto(
                    destination.getId(),
                    destination.getName(),
                    destination.getDescription(),
                    destination.getCity().getName(),
                    destination.getCity().getCountry().getName());

            destinations.add(destinationDto);
        }

        return new VehicleInfoResponseDto(
                vehicle.getVehicleNumber(),
                vehicle.getVehicleType(),
                destinations);
    }

    public VehicleBookingInfoResponseDto toBookingInfoDto(Vehicle vehicle) {
        if (vehicle == null) return null;
        Set<SeatDto> seats = new HashSet<>();

        for (Seat seat : vehicle.getSeats()) {
            SeatDto dto = seatMapper.toDto(seat);
            seats.add(dto);
        }

        return new VehicleBookingInfoResponseDto(
                vehicle.getVehicleNumber(),
                vehicle.getVehicleType(),
                vehicle.getMaxRow(),
                vehicle.getRowWidth(),
                seats);
    }
}
