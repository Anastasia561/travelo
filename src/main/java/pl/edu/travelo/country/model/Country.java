package pl.edu.travelo.country.model;

import pl.edu.travelo.validation.FieldValidator;

public class Country {
    private String name;

    public Country(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = FieldValidator.validateNullOrEmptyString(name, "Country Name");
    }
}
