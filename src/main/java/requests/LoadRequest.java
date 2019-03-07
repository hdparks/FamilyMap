package requests;

import domain.Event;
import domain.Person;
import domain.User;

/**
 * Represents a requests made to the /load endpoint
 */
public class LoadRequest implements IRequest {
    /**
     * A JSON array of User-object-friendly data (to be GSON-ed into User object)
     */
    private User[] users;

    /**
     * A JSON array of Person objects
     */
    private Person[] persons;

    /**
     * A JSON array of Event objects
     */
    private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}

