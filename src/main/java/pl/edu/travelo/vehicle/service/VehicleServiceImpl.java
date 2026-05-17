package pl.edu.travelo.vehicle.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.travelo.vehicle.dto.VehicleInfoResponseDto;
import pl.edu.travelo.vehicle.dto.VehicleResponseDto;
import pl.edu.travelo.vehicle.mapper.VehicleMapper;
import pl.edu.travelo.vehicle.repository.VehicleRepository;

@Service
class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public VehicleInfoResponseDto findById(long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toInfoDto)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
    }

    @Override
    public Page<VehicleResponseDto> findAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(vehicleMapper::toDto);
    }
}
