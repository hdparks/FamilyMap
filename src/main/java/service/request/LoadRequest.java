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
}

