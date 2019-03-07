package database_access;

import domain.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * a class for interfacing with Event data
 */
public class EventDao {

    private static Logger logger = Logger.getLogger("EventDao");

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
     * Adds an eventID to the supplied event and returns it
     * @param event the Event object to be saved
     * @throws DataAccessException if the operation fails
     */
    public Event add(Event event) throws DataAccessException{
        //  If no eventID supplied, generates one
        if(event.eventID == null){
            event.eventID = UUID.randomUUID().toString();
        }

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

            return event;

        } catch (SQLException ex) {
            logger.severe(ex.getMessage());
            throw new DataAccessException("Error while adding Event data");
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
            logger.severe(ex.getMessage());
            throw new DataAccessException("Error in deleting Event data");
        }
    }

    public Event[] getEventsByDescendant(String descendant) throws DataAccessException {
        List<Event> eventList = new ArrayList<Event>();
        String sql = "SELECT eventID, descendant, personID, latitude, longitude, country, city, eventType, year FROM events WHERE descendant = ? ";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, descendant);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //  Get everything we need
                String eventID = rs.getString(1);
                String idescendant = rs.getString(2);
                String personID = rs.getString(3);
                String latitude = rs.getString(4);
                String longitude = rs.getString(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);

                eventList.add(new Event(eventID, idescendant, personID, latitude, longitude, country, city, eventType, year));
            }

        } catch (SQLException ex) {
            logger.severe(ex.getMessage());
            throw new DataAccessException("Error getting events");
        } catch (Exception ex){
            logger.severe(ex.getMessage());
            throw ex;
        }

        Event[] eventArray = new Event[eventList.size()];
        eventArray = eventList.toArray(eventArray);
        return eventArray;
    }

    public Event getEventByEventID(String eventID) throws DataAccessException {

        String sql = "SELECT eventID, descendant, personID, latitude, longitude, country, city, eventType, year FROM events WHERE eventID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //  Get everything we need
                String ieventID = rs.getString(1);
                String descendant = rs.getString(2);
                String personID = rs.getString(3);
                String latitude = rs.getString(4);
                String longitude = rs.getString(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);

                return new Event(ieventID, descendant, personID, latitude, longitude, country, city, eventType, year);
            }
        } catch (SQLException ex) {
            logger.severe(ex.getMessage());
            throw new DataAccessException("Error getting events");
        }
        //  If nothing was found, return null
        return null;
    }

}
