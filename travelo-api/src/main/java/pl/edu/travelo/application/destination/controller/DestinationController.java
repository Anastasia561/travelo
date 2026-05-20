package pl.edu.travelo.application.destination.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.travelo.application.destination.dto.DestinationInfoResponseDto;
import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.application.destination.service.DestinationService;
import pl.edu.travelo.wrapper.ResponseWrapper;


@RestController
@RequestMapping("/destinations")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseWrapper<Page<DestinationResponseDto>> findAll(Pageable pageable) {
        return ResponseWrapper.ok(destinationService.findAll(pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseWrapper<DestinationInfoResponseDto> findById(@PathVariable Long id) {
        return ResponseWrapper.ok(destinationService.findById(id));
    }
}
