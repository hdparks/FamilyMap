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

    /**
     * An array of Event objects
     */
    Event[] data;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    /**
     * Creates an EventRequest with a given authToken string
     * @param data an array of Events
     */
    public EventRequest(Event[] data){
        this.data = data;
    }
}
