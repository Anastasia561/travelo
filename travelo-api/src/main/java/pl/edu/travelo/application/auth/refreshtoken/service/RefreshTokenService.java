package pl.edu.travelo.application.auth.refreshtoken.service;

import pl.edu.travelo.domain.model.Person;

public interface RefreshTokenService {

    void createToken(Person person, String tokenValue);

    void revokeRefreshToken(String tokenValue);
}
