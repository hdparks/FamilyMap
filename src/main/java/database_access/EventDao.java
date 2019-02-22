package database_access;

import domain.Event;

import java.util.List;

/**
 * a class for interfacing with Event data
 */
public class EventDao {

    /**
     * Clears all Event data
     * @throws DataAccessException if the operation fails
     */
    public void clear() throws DataAccessException{

    }


    /**
     * Adds new Event data
     * @param event the Event object to be saved
     * @throws DataAccessException if the operation fails
     */
    public void add(Event event) throws DataAccessException{

    }

    /**
     * Deletes the matching event if found, throws exception if not found
     * @param event the event to be deleted
     * @throws DataAccessException if operation fails, ie if the event is not found
     */
    public void delete(Event event) throws DataAccessException{

    }

    /**
     * Returns a list of all Event objects related to the given personID
     * @param personID the personID of the Person whose events are returned
     * @return eventsList a list of all events related to the Person
     */
    public List<Event> getEventsByPersonID(String personID){
        return null;
    }

    /**
     * Returns a list of all Event objects with the given eventType
     * @param eventType the eventType of all Events to be returned
     * @return eventsList a list of all Events of type {eventType}
     */
    public List<Event> getEventsByType(String eventType){
        return null;
    }


}
