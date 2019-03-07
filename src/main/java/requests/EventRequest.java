package requests;

import domain.Event;

/**
 * Represents a requests to the /event endpoint
 */
public class EventRequest implements IRequest {
    /**
     * Non-empty string
     */
    String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public EventRequest(String authString){
        this.authToken = authString;
    }

}
