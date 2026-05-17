package pl.edu.travelo.destination.mapper;

import org.springframework.stereotype.Component;
import pl.edu.travelo.destination.dto.DestinationInfoResponseDto;
import pl.edu.travelo.destination.dto.DestinationResponseDto;
import pl.edu.travelo.domain.model.Destination;
import pl.edu.travelo.domain.model.Trip;
import pl.edu.travelo.domain.model.Vehicle;
import pl.edu.travelo.vehicle.dto.VehicleResponseDto;

import java.util.HashSet;
import java.util.Set;

@Component
public class DestinationMapper {
    public DestinationInfoResponseDto toInfoDto(Destination destination) {
        if (destination == null) return null;

        Set<VehicleResponseDto> vehicles = new HashSet<>();
        for (Trip trip : destination.getTrips()) {
            Vehicle vehicle = trip.getVehicle();
            VehicleResponseDto vehicleResponseDto = new VehicleResponseDto(
                    vehicle.getVehicleNumber(), vehicle.getVehicleType(),
                    vehicle.getMaxRow(), vehicle.getRowWidth()
            );
            vehicles.add(vehicleResponseDto);
        }

        return new DestinationInfoResponseDto(destination.getName(),
                destination.getDescription(), destination.getCity().getName(),
                destination.getCity().getCountry().getName(), vehicles);
    }

    public DestinationResponseDto toDto(Destination destination) {
        if (destination == null) return null;

        return new DestinationResponseDto(destination.getName(),
                destination.getDescription(), destination.getCity().getName(),
                destination.getCity().getCountry().getName());
    }
}
