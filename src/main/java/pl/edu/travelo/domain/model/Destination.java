package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

public class Destination {
    private String name;
    private String description;

    private City city;
    private final Set<Trip> trips = new HashSet<>();

    public Destination(String name, String description, City city) {
        setName(name);
        setDescription(description);
        assignCity(city);
    }

    public Destination(String name, String description, City city, Set<Trip> trips) {
        setName(name);
        setDescription(description);
        assignCity(city);

        FieldValidator.validateObjectNotNull(city, "Trips list");
        for (Trip trip : trips) {
            addTrip(trip);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = FieldValidator.validateNullOrEmptyString(name, "Destination name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = FieldValidator.validateNullOrEmptyString(description, "Description");
    }

    public void assignCity(City newCity) {
        FieldValidator.validateObjectNotNull(newCity, "City");

        if (this.city == newCity) return;

        if (this.city != null) {
            this.city.removeDestination(this);
        }

        this.city = newCity;
        this.city.addDestination(this);
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

    public City getCity() {
        return city;
    }

    public Set<Trip> getTrips() {
        return new HashSet<>(trips);
    }
}
