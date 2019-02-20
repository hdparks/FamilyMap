package service.response;

import domain.Event;

import java.util.List;

/**
 * Represents a response to the /event endpoint
 */
public class EventResponse extends Response {
    /**
     * A list of Event objects
     */
    List<Event> events;

    /**
     * Creates a valid response
     * @param events the requested list of Event objects
     */
    EventResponse(List<Event> events){
        super(true);
        this.events = events;
    }

    /**
     * Creates a failing response
     * @param message a description of the failure
     */
    EventResponse(String message){
        super(message, false);
    }
}
