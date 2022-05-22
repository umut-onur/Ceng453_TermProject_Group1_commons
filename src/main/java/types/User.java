package types;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * User implements the UserDetails interface in Spring Security so that
 * it can be used by it without a problem.
 */
public class User implements UserDetails {
    private String id;
    private final String username;
    private String password;

    @JsonCreator
    public User(
            @JsonProperty("id") final String id,
            @JsonProperty("username") final String username,
            @JsonProperty("password") final String password) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    /**
     * Parses a json string into a User object and returns it.
     *
     * @param json The raw json string
     * @return The parsed User object
     * @throws JsonProcessingException If given json string is not a representation for the User class.
     */
    public static User parseJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, User.class);
    }

    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonGetter
    public String getId() {
        return this.id;
    }

    @JsonIgnore
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @JsonGetter
    public String getPassword() {
        return this.password;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonGetter
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User o) {
            return this.id.equals(o.id) &&
                    this.username.equals(o.username) &&
                    this.password.equals(o.password);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("id=%s, username=%s, password=%s", this.id, this.username, this.password);
    }
}

