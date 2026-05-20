package pl.edu.travelo.application.person.service;

import pl.edu.travelo.domain.model.Person;

public interface PersonService {
    Person findById(long id);
}
