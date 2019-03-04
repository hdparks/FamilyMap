package services.response;

import domain.Event;

/**
 * Represents a response to the /event/[eventID] endpoint
 */
public class EventIDResponse extends Response {
    /**
     * An Event object
     */
    Event event;

    /**
     * Creates a valid EventIDResponse object
     * @param event the Event object with desired ID
     */
    EventIDResponse(Event event){
        super(true);
        this.event = event;
    }

    /**
     * Creates a failing EventIDResponse object
     * @param message a description of what went wrong
     */
    EventIDResponse(String message){
        super(message, false);
    }

}
