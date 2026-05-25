package pl.edu.travelo.application.destination;

import org.springframework.stereotype.Component;
import pl.edu.travelo.application.destination.dto.DestinationResponseDto;
import pl.edu.travelo.domain.model.Destination;

@Component
public class DestinationMapper {

    public DestinationResponseDto toDto(Destination destination) {
        if (destination == null) return null;

        return new DestinationResponseDto(
                destination.getId(),
                destination.getName(),
                destination.getDescription(),
                destination.getCity().getName(),
                destination.getCity().getCountry().getName());
    }
}
