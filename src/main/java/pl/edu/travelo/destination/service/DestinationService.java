package pl.edu.travelo.destination.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.travelo.destination.dto.DestinationInfoResponseDto;
import pl.edu.travelo.destination.dto.DestinationResponseDto;

public interface DestinationService {
    DestinationInfoResponseDto findById(long id);
    Page<DestinationResponseDto> findAll(Pageable pageable);
}
