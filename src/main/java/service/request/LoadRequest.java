package service.request;

/**
 * Represents a request made to the /load endpoint
 */
public class LoadRequest implements Request {
    /**
     * A JSON array of User-object-friendly data (to be GSON-ed into User object)
     */
    String users;

    /**
     * A JSON array of Person objects
     */
    String persons;

    /**
     * A JSON array of Event objects
     */
    String events;

    /**
     * Creates a LoadRequest with separated JSON lists
     * @param users a JSON list representing Users
     * @param persons a JSON list representing Persons
     * @param events a JSON list representing Events
     */
    LoadRequest(String users, String persons, String events){
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

}

