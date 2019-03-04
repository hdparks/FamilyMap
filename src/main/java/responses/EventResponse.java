package responses;

import domain.Event;

import java.util.List;

/**
 * Represents a responses to the /event endpoint
 */
public class EventResponse extends Response {
    /**
     * A list of Event objects
     */
    List<Event> events;

    /**
     * Creates a valid responses
     * @param events the requested list of Event objects
     */
    EventResponse(List<Event> events){
        super(true);
        this.events = events;
    }

    /**
     * Creates a failing responses
     * @param message a description of the failure
     */
    EventResponse(String message){
        super(message, false);
    }
}
