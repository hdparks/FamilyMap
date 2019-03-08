package responses;

import domain.Event;

/**
 * Represents a responses to the /event/[eventID] endpoint
 */
public class EventIDResponse extends Response {

    /**
     * An Event object
     */

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


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
