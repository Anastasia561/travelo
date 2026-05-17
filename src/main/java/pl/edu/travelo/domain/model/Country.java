package pl.edu.travelo.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pl.edu.travelo.domain.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;


    @OneToMany(mappedBy = "country")
    private final Set<City> cities = new HashSet<>();

    public Country(String name, String cityName) {
        setName(name);
        FieldValidator.validateNullOrEmptyString(cityName, "City name");
        City city = new City(cityName, this);
        this.cities.add(city);
    }

    public Country(String name, Set<City> cities) {
        this.name = name;
        FieldValidator.validateObjectNotNull(cities, "Cities");
        for (City city : cities) {
            addCity(city);
        }
    }

    protected Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = FieldValidator.validateNullOrEmptyString(name, "Country Name");
    }

    void addCity(City city) {
        FieldValidator.validateObjectNotNull(city, "City");
        this.cities.add(city);
    }

    void removeCity(City city) {
        FieldValidator.validateObjectNotNull(city, "City");

        if (!this.cities.contains(city)) return;

        if (this.cities.size() == 1) {
            throw new IllegalStateException("Cannot remove city " + city.getName() +
                    ". Country must have at least one city");
        }
        this.cities.remove(city);
    }

    public Set<City> getCities() {
        return new HashSet<>(cities);
    }
}
