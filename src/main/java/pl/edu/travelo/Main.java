package pl.edu.travelo;


import pl.edu.travelo.domain.model.City;
import pl.edu.travelo.domain.model.Country;

public class Main {
    public static void main(String[] args) {
        Country country = new Country("France");

        Country country2 = new Country("Germany");

        City c1 = new City("Paris", country);

        City c2 = new City("Berlin", country2);
        City c3 = new City("Berlin2", country2);
//
//        country2.addCity(c2);
//        country2.removeCity(c1);

//        c2.assignCountry(country);
//
//        System.out.println(c2);
//        System.out.println(country);
//        System.out.println(country2);
    }
}
