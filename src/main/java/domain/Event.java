package domain;

/**
 * Represents an event
 */
public class Event {

    /**
     * Unique identifier for this event (non-empty string)
     */
    public String eventID;

    /**
     * User (Username) to which this person belongs
     */
    public String descendant;

    /**
     * ID of person to which this event belongs
     */
    public String personID;

    /**
     * Latitude of event’s location
     */
    public String latitude;

    /**
     *  Longitude of event’s location
     */
    public String longitude;

    /**
     * Country in which event occurred
     */
    public String country;

    /**
     * City in which event occurred
     */
    public String city;

    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    public String eventType;

    /**
     * Year in which event occurred
     */
    public int year;

    /**
     * ONLY to be used when retrieving Event from the database, since
     * the database is what tracks the creation of eventID
     * @param eventID id
     * @param descendant username
     * @param personID id
     * @param latitude number
     * @param longitude number
     * @param country string
     * @param city string
     * @param eventType string
     * @param year int
     */
    public Event(String eventID, String descendant, String personID, String latitude, String longitude, String country, String city, String eventType, int year){
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Used to create a new Event object, without knowing its database-endowed id
     * @param descendant username
     * @param personID id
     * @param latitude number
     * @param longitude number
     * @param country string
     * @param city string
     * @param eventType string
     * @param year int
     */
    public Event(String descendant, String personID, String latitude, String longitude, String country, String city, String eventType, int year){
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }


}
