package pl.edu.travelo.application.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {
    private String secret;
    private long accessTokenExpirationTimeMs;
    private long refreshTokenExpirationTimeMs;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getRefreshTokenExpirationTimeMs() {
        return refreshTokenExpirationTimeMs;
    }

    public void setRefreshTokenExpirationTimeMs(long refreshTokenExpirationTimeMs) {
        this.refreshTokenExpirationTimeMs = refreshTokenExpirationTimeMs;
    }

    public long getAccessTokenExpirationTimeMs() {
        return accessTokenExpirationTimeMs;
    }

    public void setAccessTokenExpirationTimeMs(long accessTokenExpirationTimeMs) {
        this.accessTokenExpirationTimeMs = accessTokenExpirationTimeMs;
    }

    public long getRefreshTokenExpirationTimeSec() {
        return refreshTokenExpirationTimeMs / 1000;
    }
}
