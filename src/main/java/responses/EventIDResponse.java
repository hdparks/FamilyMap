package responses;

import domain.Event;

/**
 * Represents a responses to the /event/[eventID] endpoint
 */
public class EventIDResponse extends Response {
    /**
     * An Event object
     */
    String descendant;

    String eventID;
    String personID;
    String latitude;
    String longitude;
    String country;
    String city;
    int year;

    /**
     * Creates a valid EventIDResponse object
     * @param event the Event object with desired ID
     */
    public EventIDResponse(Event event){
        super(true);
        this.descendant = event.descendant;
        this.eventID = event.eventID;
        this.personID = event.personID;
        this.latitude = event.latitude;
        this.longitude = event.longitude;
        this.country = event.country;
        this.city = event.city;
        this.year = event.year;
    }

    /**
     * Creates a failing EventIDResponse object
     * @param message a description of what went wrong
     */
    public EventIDResponse(String message){
        super(message, false);
    }

}
