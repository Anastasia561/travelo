package pl.edu.travelo.destination.dto;

public record DestinationResponseDto(
        String name,
        String description,
        String cityName,
        String countryName
) {
}
