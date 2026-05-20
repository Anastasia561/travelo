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
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;


    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "destination")
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

    protected Destination() {
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
