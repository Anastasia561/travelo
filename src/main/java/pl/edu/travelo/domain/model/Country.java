package pl.edu.travelo.domain.model;

import pl.edu.travelo.validation.FieldValidator;

import java.util.HashSet;
import java.util.Set;

public class Country {
    private String name;

    private final Set<City> cities = new HashSet<>();

    public Country(String name) {
        setName(name);
    }

    public Country(String name, Set<City> cities) {
        this.name = name;
        FieldValidator.validateObjectNotNull(cities, "Cities");
        for (City city : cities) {
            addCity(city);
        }
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
