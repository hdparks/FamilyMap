package service.request;

/**
 * Represents a request to the /event endpoint
 */
public class EventRequest implements Request {
    /**
     * Non-empty string
     */
    String authToken;

    /**
     * Creates an EventRequest with a given authToken string
     * @param authToken the given authToken string
     */
    EventRequest(String authToken){
        this.authToken = authToken;
    }
}
