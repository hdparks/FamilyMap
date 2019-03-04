package database_access;

import domain.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * a class for interfacing with Event data
 */
public class EventDao {

    Connection conn;

    /**
     * Connects an EventDAO object to a Database object with connection
     * @param conn a database connection
     */
    public EventDao(Connection conn){ this.conn = conn; }

    /**
     * Clears all Event data
     * @throws DataAccessException if the operation fails
     */
    public void clear() throws DataAccessException{
        try{
            conn.createStatement().executeUpdate("DELETE FROM events");
        } catch (SQLException ex){
            throw new DataAccessException("Error occurred while clearing events table.");
        }
    }


    /**
     * Adds new Event data
     * @param event the Event object to be saved
     * @throws DataAccessException if the operation fails
     */
    public void add(Event event) throws DataAccessException{
        String sql = "INSERT INTO events (eventID, descendant, personID, latitude, longitude, country, city, eventType, year) "+
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, event.eventID);
            stmt.setString(2,event.descendant);
            stmt.setString(3,event.personID);
            stmt.setString(4,event.latitude);
            stmt.setString(5,event.longitude);
            stmt.setString(6,event.country);
            stmt.setString(7,event.city);
            stmt.setString(8,event.eventType);
            stmt.setInt(9,event.year);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            throw new DataAccessException("Error while adding Event: \n"+ex.getMessage());
        }
    }

    /**
     * Deletes all matching events if found, throws exception if not found
     * @param descendant the username of the descendant of the event to be deleted
     * @throws DataAccessException if operation fails, ie if the event is not found
     */
    public void deleteByDescendant(String descendant) throws DataAccessException{
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE descendant = ?");
            stmt.setString(1,descendant);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error in deleting Events: \n"+ex.getMessage());
        }
    }

    /**
     * Returns a list of all Event objects related to the given personID
     * @param personID the personID of the Person whose events are returned
     * @return eventsList a list of all events related to the Person
     */
    public List<Event> getEventsByPersonID(String personID) throws DataAccessException{
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE personID = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,personID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                //  Get everything we need
                String eventID = rs.getString(1);
                String descendant = rs.getString(2);
                String ePersonID = rs.getString(3);
                String latitude = rs.getString(4);
                String longitude = rs.getString(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);

                eventList.add(new Event(eventID,descendant,ePersonID,latitude,longitude,country,city,eventType,year));
            }

        } catch (SQLException ex){
            throw new DataAccessException("Error getting events:\n"+ex.getMessage());
        }

        return eventList;
    }

    /**
     * Returns a list of all Event objects with the given eventType
     * @param eventType the eventType of all Events to be returned
     * @return eventsList a list of all Events of type {eventType}
     */
    public List<Event> getEventsByType(String eventType) throws DataAccessException {

        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE eventType = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventType);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //  Get everything we need
                String eventID = rs.getString(1);
                String descendant = rs.getString(2);
                String personID = rs.getString(3);
                String latitude = rs.getString(4);
                String longitude = rs.getString(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String nEventType = rs.getString(8);
                int year = rs.getInt(9);

                eventList.add(new Event(eventID, descendant, personID, latitude, longitude, country, city, nEventType, year));
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Error getting events:\n" + ex.getMessage());
        }

        return eventList;
    }

}
