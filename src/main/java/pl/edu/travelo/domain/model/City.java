package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

public class City {
    private String name;

    private Country country;
    private final Set<Destination> destinations = new HashSet<>();
    private final Set<Trip> trips = new HashSet<>();

    public City(String name, Country country) {
        setName(name);
        assignCountry(country);
    }

    public City(String name, Country country, Set<Destination> destinations, Set<Trip> trips) {
        this(name, country);

        FieldValidator.validateObjectNotNull(destinations, "Destinations List");
        for (Destination destination : destinations) {
            addDestination(destination);
        }

        FieldValidator.validateObjectNotNull(trips, "Trips List");
        for (Trip trip : trips) {
            addTrip(trip);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = FieldValidator.validateNullOrEmptyString(name, "City name");
    }

    public Country getCountry() {
        return country;
    }

    public void assignCountry(Country newCountry) {
        FieldValidator.validateObjectNotNull(newCountry, "Country");

        if (this.country == newCountry) return;

        if (this.country != null) {
            this.country.removeCity(this);
        }

        this.country = newCountry;
        this.country.addCity(this);
    }

    void addDestination(Destination destination) {
        FieldValidator.validateObjectNotNull(destination, "Destination");
        this.destinations.add(destination);
    }

    void removeDestination(Destination destination) {
        FieldValidator.validateObjectNotNull(destination, "Destination");
        if (!this.destinations.contains(destination)) return;
        this.destinations.remove(destination);
    }

    public Set<Destination> getDestinations() {
        return new HashSet<>(destinations);
    }

    void addTrip(Trip trip) {
        FieldValidator.validateObjectNotNull(trip, "Trip");
        this.trips.add(trip);
    }

    void removeTrip(Trip trip) {
        FieldValidator.validateObjectNotNull(trip, "Trip");
        if (!this.trips.contains(trip)) return;
        this.trips.remove(trip);
    }

    public Set<Trip> getTrips() {
        return new HashSet<>(trips);
    }
}
