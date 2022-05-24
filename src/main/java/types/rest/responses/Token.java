package types.rest.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    private final String accessToken;
    private final Long expiresAt;
    private final String userId;
    private final String refreshToken;

    @JsonCreator
    public Token(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_at") Long expiresAt,
            @JsonProperty("user_id") String userId,
            @JsonProperty("refresh_token") String refreshToken
    ) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    @JsonGetter
    public String getAccessToken() {
        return accessToken;
    }

    @JsonGetter
    public Long getExpiresAt() {
        return expiresAt;
    }

    @JsonGetter
    public String getUserId() {
        return userId;
    }

    @JsonGetter
    public String getRefreshToken() {
        return refreshToken;
    }
}
