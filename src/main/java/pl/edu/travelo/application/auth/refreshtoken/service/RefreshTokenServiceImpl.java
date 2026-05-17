package pl.edu.travelo.application.auth.refreshtoken.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.auth.refreshtoken.repository.RefreshTokenRepository;
import pl.edu.travelo.application.auth.refreshtoken.model.RefreshToken;
import pl.edu.travelo.domain.model.Person;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public void createToken(Person person, String tokenValue) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(tokenValue);
        refreshToken.setPerson(person);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public void revokeRefreshToken(String tokenValue) {
        RefreshToken token = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
        refreshTokenRepository.delete(token);
    }
}
