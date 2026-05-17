package pl.edu.travelo.application.destination.dto;

public record DestinationResponseDto(
        String name,
        String description,
        String cityName,
        String countryName
) {
}
