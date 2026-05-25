package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;


    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "city")
    private final Set<Destination> destinations = new HashSet<>();

    @OneToMany(mappedBy = "startCity")
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

    protected City() {
    }

    public long getId() {
        return id;
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
