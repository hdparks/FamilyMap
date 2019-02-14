package domain;

/**
 * Represents an event
 */
public class Event {
    /**
     * Unique identifier for this event (non-empty string)
     */
    String eventID;

    /**
     * User (Username) to which this person belongs
     */
    String descendant;

    /**
     * ID of person to which this event belongs
     */
    String personID;

    /**
     * Latitude of event’s location
     */
    String latitude;

    /**
     *  Longitude of event’s location
     */
    String longitude;

    /**
     * Country in which event occurred
     */
    String country;

    /**
     * City in which event occurred
     */
    String city;

    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    String eventType;

    /**
     * Year in which event occurred
     */
    String year;
}
