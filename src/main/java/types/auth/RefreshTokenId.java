package types.auth;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * RefreshTokenId is a wrapper for the primary key of the RefreshTokens table for use with JPA.
 * JPA requires that multi-field primary keys be represented this way.
 */
@Embeddable
public class RefreshTokenId implements Serializable {
    private String userId;
    private String token;

    public RefreshTokenId() {}

    public RefreshTokenId(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
