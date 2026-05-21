package pl.edu.travelo.application.destination.dto;

public record DestinationResponseDto(
        long id,
        String name,
        String description,
        String cityName,
        String countryName
) {
}
