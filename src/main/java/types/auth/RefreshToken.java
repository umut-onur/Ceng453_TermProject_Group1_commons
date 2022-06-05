package types.auth;

import javax.persistence.*;

@Entity
@Table(name = "RefreshTokens")
public class RefreshToken {
    @EmbeddedId
    private RefreshTokenId refreshTokenId;  // multi-column primary key

    public RefreshToken() {
        this.refreshTokenId = new RefreshTokenId();
    }

    public RefreshToken(String userId, String token) {
        this.refreshTokenId = new RefreshTokenId(userId, token);
    }

    public String getUserId() {
        return this.refreshTokenId.getUserId();
    }

    public String getToken() {
        return this.refreshTokenId.getToken();
    }

    public void setUserId(String userId) {
        this.refreshTokenId.setUserId(userId);
    }

    public void setToken(String token) {
        this.refreshTokenId.setToken(token);
    }
}
