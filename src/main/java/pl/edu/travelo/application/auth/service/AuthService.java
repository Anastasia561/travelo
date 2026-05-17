package pl.edu.travelo.application.auth.service;

import pl.edu.travelo.application.auth.dto.AuthRequestDto;
import pl.edu.travelo.application.auth.dto.TokenResponseDto;

public interface AuthService {
    TokenResponseDto login(AuthRequestDto request);

    TokenResponseDto refresh(String refreshToken);

    void logout(String refreshToken);
}
