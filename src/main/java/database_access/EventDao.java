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
    void clear() throws DataAccessException{

    }

    /**
     * Returns a list of all Event objects related to the given personID
     * @param personID the personID of the Person whose events are returned
     * @return eventsList a list of all events related to the Person
     */
    List<Event> getEventsByPersonID(String personID){
        return null;
    }

    /**
     * Returns a list of all Event objects with the given eventType
     * @param eventType the eventType of all Events to be returned
     * @return eventsList a list of all Events of type {eventType}
     */
    List<Event> getEventsByType(String eventType){
        return null;
    }


}
