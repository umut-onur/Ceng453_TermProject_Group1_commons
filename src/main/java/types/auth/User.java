package types.auth;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * User implements the UserDetails interface in Spring Security so that
 * it can be used by it without a problem.
 */
@Entity
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "passwordCreatedAt", nullable = true)
    private Timestamp passwordCreatedAt;

    @Column(name = "passwordResetMailSent", nullable = true)
    private Boolean passwordResetMailSent;

    public User() {}

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

    @JsonIgnore
    public Timestamp getPasswordCreatedAt() {
        return passwordCreatedAt;
    }

    @JsonIgnore
    public void setPasswordCreatedAt(Timestamp passwordCreatedAt) {
        this.passwordCreatedAt = passwordCreatedAt;
    }

    @JsonIgnore
    public Boolean getPasswordResetMailSent() {
        return passwordResetMailSent;
    }

    @JsonIgnore
    public void setPasswordResetMailSent(Boolean passwordResetMailSent) {
        this.passwordResetMailSent = passwordResetMailSent;
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

