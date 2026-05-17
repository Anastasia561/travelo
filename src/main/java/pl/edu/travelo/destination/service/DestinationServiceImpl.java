package pl.edu.travelo.destination.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.travelo.destination.dto.DestinationInfoResponseDto;
import pl.edu.travelo.destination.dto.DestinationResponseDto;
import pl.edu.travelo.destination.mapper.DestinationMapper;
import pl.edu.travelo.destination.repository.DestinationRepository;

@Service
class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    public DestinationServiceImpl(DestinationRepository destinationRepository, DestinationMapper destinationMapper) {
        this.destinationRepository = destinationRepository;
        this.destinationMapper = destinationMapper;
    }

    @Override
    public DestinationInfoResponseDto findById(long id) {
        return destinationRepository.findById(id)
                .map(destinationMapper::toInfoDto)
                .orElseThrow(() -> new EntityNotFoundException("Destination not found"));
    }

    @Override
    public Page<DestinationResponseDto> findAll(Pageable pageable) {
        return destinationRepository.findAll(pageable).map(destinationMapper::toDto);
    }
}
