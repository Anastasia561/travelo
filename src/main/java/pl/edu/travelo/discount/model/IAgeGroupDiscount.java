package pl.edu.travelo.discount.model;

public interface IAgeGroupDiscount {
    String getDescription();

    void setDescription(String description);

    AgeGroup getGroup();

    void setGroup(AgeGroup date);
}
