package pl.edu.travelo.application.vehicle;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.seat.SeatMapper;
import pl.edu.travelo.domain.model.Seat;
import pl.edu.travelo.domain.model.Vehicle;
import pl.edu.travelo.application.seat.dto.SeatDto;
import pl.edu.travelo.application.vehicle.dto.VehicleBookingInfoResponseDto;

import java.util.HashSet;
import java.util.Set;

@Component
public class VehicleMapper {
    private final SeatMapper seatMapper;

    public VehicleMapper(SeatMapper seatMapper) {
        this.seatMapper = seatMapper;
    }

    public VehicleBookingInfoResponseDto toBookingInfoDto(Vehicle vehicle, long tripId) {
        if (vehicle == null) return null;
        Set<SeatDto> seats = new HashSet<>();

        for (Seat seat : vehicle.getSeats()) {
            SeatDto dto = seatMapper.toDto(seat, tripId);
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
