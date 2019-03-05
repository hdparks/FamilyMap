package domain;

/**
 * Represents an event
 */
public class Event {

    /**
     * A counter that allows for unique PersonID's
     */
    private static int counterID = 0;

    /**
     * Assigns a unique PersonID value each time it is called.
     * @return a unique PersonID
     */
    public static String getNewEventID(){
        counterID += 1;
        return Integer.toString(counterID);
    }


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
}
