package pl.edu.travelo.destination.model;

import pl.edu.travelo.validation.FieldValidator;

public class Destination {
    private String name;
    private String description;

    public Destination(String name, String description) {
        setName(name);
        setDescription(description);
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
}
