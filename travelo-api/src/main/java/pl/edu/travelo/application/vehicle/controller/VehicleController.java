package pl.edu.travelo.application.vehicle.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.vehicle.dto.VehicleInfoResponseDto;
import pl.edu.travelo.application.vehicle.dto.VehicleResponseDto;
import pl.edu.travelo.application.vehicle.service.VehicleService;
import pl.edu.travelo.wrapper.ResponseWrapper;


@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseWrapper<Page<VehicleResponseDto>> getAll(Pageable pageable) {
        return ResponseWrapper.ok(vehicleService.findAll(pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseWrapper<VehicleInfoResponseDto> findById(@PathVariable Long id) {
        return ResponseWrapper.ok(vehicleService.findById(id));
    }
}
