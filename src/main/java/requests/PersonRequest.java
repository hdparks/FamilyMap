package requests;

import java.util.Objects;

/**
 * Represents a requests to the /person endpoint
 */
public class PersonRequest implements IRequest {
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * The authToken of the current user
     */
    private String authToken;

    /**
     * Construct a PersonRequest
     * @param authToken the authToken of the current user
     */
    public PersonRequest(String authToken){
        this.authToken =  authToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRequest that = (PersonRequest) o;
        return authToken.equals(that.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken);
    }
}
