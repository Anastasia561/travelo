package pl.edu.travelo.application.auth.service;

import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.edu.travelo.application.auth.core.CustomUserDetails;
import pl.edu.travelo.application.auth.dto.AuthRequestDto;
import pl.edu.travelo.application.auth.dto.TokenResponseDto;
import pl.edu.travelo.application.auth.jwt.service.JwtService;
import pl.edu.travelo.application.auth.refreshtoken.service.RefreshTokenService;
import pl.edu.travelo.application.person.service.PersonService;
import pl.edu.travelo.domain.model.Person;
import pl.edu.travelo.exception.InvalidRefreshTokenException;

@Service
class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final PersonService personService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtService jwtService, UserDetailsService userDetailsService,
                           PersonService personService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.personService = personService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public TokenResponseDto login(AuthRequestDto request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(customUser);
            String refreshToken = jwtService.generateRefreshToken(customUser);

            Person person = personService.findById(customUser.getId());

            refreshTokenService.createToken(person, refreshToken);

            return new TokenResponseDto(accessToken, refreshToken);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    public TokenResponseDto refresh(String refreshToken) {
        try {
            if (!jwtService.isRefreshToken(refreshToken)) {
                throw new InvalidRefreshTokenException("Token is not a refresh token");
            }

            String username = jwtService.extractUsername(refreshToken);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

            if (!jwtService.isTokenValid(refreshToken, userDetails)) {
                throw new InvalidRefreshTokenException("Refresh token is invalid");
            }
            String newAccess = jwtService.generateAccessToken(userDetails);

            return new TokenResponseDto(newAccess, refreshToken);

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
    }

    @Override
    public void logout(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        if (!jwtService.isRefreshToken(refreshToken) || !jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        refreshTokenService.revokeRefreshToken(refreshToken);
    }
}
