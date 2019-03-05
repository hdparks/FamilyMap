package requests;

/**
 * Represents a requests to the /event endpoint
 */
public class EventRequest implements IRequest {
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
