package pl.edu.travelo.application.destination.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.travelo.application.destination.dto.DestinationInfoResponseDto;
import pl.edu.travelo.application.destination.dto.DestinationResponseDto;

public interface DestinationService {
    DestinationInfoResponseDto findById(long id);
    Page<DestinationResponseDto> findAll(Pageable pageable);
}
