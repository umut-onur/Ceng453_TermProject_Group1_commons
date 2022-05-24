package types.rest.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordRequest {
    private final String newPassword;

    @JsonCreator
    public ResetPasswordRequest(@JsonProperty("new_password") String newPassword) {
        this.newPassword = newPassword;
    }

    @JsonGetter
    public String getNewPassword() {
        return this.newPassword;
    }
}
