package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterUserRequest is the type representing the request body for /register
 * endpoint.
 */
public class RegisterUserRequest {
    private final String username;
    private final String email;
    private final String password;

    @JsonCreator
    public RegisterUserRequest(
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @JsonGetter
    public String getUsername() {
        return username;
    }

    @JsonGetter
    public String getEmail() {
        return email;
    }

    @JsonGetter
    public String getPassword() {
        return password;
    }
}
