package pl.edu.travelo.vehicle.mapper;

import org.springframework.stereotype.Component;
import pl.edu.travelo.destination.dto.DestinationResponseDto;
import pl.edu.travelo.domain.model.Destination;
import pl.edu.travelo.domain.model.Trip;
import pl.edu.travelo.domain.model.Vehicle;
import pl.edu.travelo.vehicle.dto.VehicleInfoResponseDto;
import pl.edu.travelo.vehicle.dto.VehicleResponseDto;

import java.util.HashSet;
import java.util.Set;

@Component
public class VehicleMapper {
    public VehicleResponseDto toDto(Vehicle vehicle) {
        if (vehicle == null) return null;

        return new VehicleResponseDto(vehicle.getVehicleNumber(),
                vehicle.getVehicleType(), vehicle.getMaxRow(), vehicle.getRowWidth());
    }

    public VehicleInfoResponseDto toInfoDto(Vehicle vehicle) {
        if (vehicle == null) return null;
        Set<DestinationResponseDto> destinations = new HashSet<>();

        for (Trip trip : vehicle.getTrips()) {
            Destination destination = trip.getDestination();

            DestinationResponseDto destinationDto = new DestinationResponseDto(destination.getName(),
                    destination.getDescription(), destination.getCity().getName(),
                    destination.getCity().getCountry().getName());

            destinations.add(destinationDto);
        }

        return new VehicleInfoResponseDto(vehicle.getVehicleNumber(), vehicle.getVehicleType(),
                vehicle.getMaxRow(), vehicle.getRowWidth(), destinations);
    }
}
