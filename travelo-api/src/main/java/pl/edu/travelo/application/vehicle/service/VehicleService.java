package pl.edu.travelo.application.vehicle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.travelo.application.vehicle.dto.VehicleInfoResponseDto;
import pl.edu.travelo.application.vehicle.dto.VehicleResponseDto;

public interface VehicleService {
    VehicleInfoResponseDto findById(long id);

    Page<VehicleResponseDto> findAll(Pageable pageable);
}
