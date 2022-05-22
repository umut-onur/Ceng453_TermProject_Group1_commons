package types.websocket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.lang.NonNull;

/**
 * HelloResponse is the response to the websocket message <code>HelloMessage</code>.
 */
public class HelloResponse {
    private boolean ok;     // indicates whether hello message was successful or not
    private String error;   // when hello message was not successful, this error contains explanation of why

    public HelloResponse() {}

    public HelloResponse(boolean ok, @NonNull String error) {
        this.ok = ok;
        this.error = error;
    }

    public HelloResponse(boolean ok, @NonNull String format, @NonNull Object... args) {
        this.ok = ok;
        this.error = String.format(format, args);
    }

    @JsonGetter
    public boolean isOk() {
        return ok;
    }

    @JsonGetter
    public String getError() {
        return error;
    }

    @JsonSetter
    public void setOk(boolean ok) {
        this.ok = ok;
    }

    @JsonSetter
    public void setError(String error) {
        this.error = error;
    }
}

