package pl.edu.travelo.application.auth.refreshtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.travelo.application.auth.refreshtoken.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
