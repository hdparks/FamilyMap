package requests;

/**
 * Represents a requests made to the /event/[eventID] endpoint
 */
public class EventIDRequest implements Request{
    /**
     * Non-empty string
     */
    String authToken;

    /**
     * Non-empty string
     */
    String eventID;

    /**
     * Creates new EventIDRequest object
     * @param authToken the supplied authToken
     * @param eventID the supplied eventID
     */
    EventIDRequest(String authToken, String eventID){
        this.authToken = authToken;
        this.eventID = eventID;
    }
}
