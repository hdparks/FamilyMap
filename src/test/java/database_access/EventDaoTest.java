package database_access;

import domain.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class EventDaoTest {

    class EventDaoTestException extends Exception{

    }

    Database db = new Database();
    Event event = new Event("eventID", "descendant", "personID", "latitude", "longitude", "country", "city", "eventType", 2000);

    @Before
    public void setUp() throws Exception {
        db.clearTables();
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    public void clear() {
    }

    @Test
    public void add() throws Exception {
        //  Add an event to an empty table
        Connection conn = db.openConnection();
        EventDao eventDao = new EventDao(conn);
        eventDao.add(event);

        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM events");
        boolean valid = rs.next();
        assert(valid);
    }

    @Test(expected = EventDaoTestException.class)
    public void addIdenticalEvents() throws Exception {
        Connection conn = db.openConnection();
        EventDao eventDao = new EventDao(conn);
        //  Add an event
        eventDao.add(event);

        try {
            //  Duplicating should throw an exception.
            eventDao.add(event);
        } catch (Exception ex){
            throw new EventDaoTestException();
        }
    }

    @Test
    public void deleteByDescendant() throws Exception {
        Connection conn = db.openConnection();

        EventDao eventDao = new EventDao(conn);

        //  Add an event
        eventDao.add(event);

        eventDao.deleteByDescendant(event.descendant);

        //  Assert that the database is empty
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM events");
        boolean isEmpty = !rs.next();
        assert(isEmpty);
    }

    @Test
    public void deleteByNonexistantDescendant() throws Exception {
        //  Database starts empty, so we just go.
        Connection conn = db.openConnection();

        EventDao eventDao = new EventDao(conn);

        try {
            eventDao.deleteByDescendant(event.descendant);

        } catch (Exception ex){
            //  If this operation throws anything, it fails.
            fail();
        }
    }

    @Test
    public void getEventsByDescendant() throws Exception {
        Connection conn = db.openConnection();
        EventDao eventDao = new EventDao(conn);

        //  Populate with generic event
        eventDao.add(event);

        //  Query for it
        Event actual = eventDao.getEventsByDescendant(event.descendant)[0];

        //  Ensure they come out the same they went in
        assertEquals(event, actual);

    }

    @Test
    public void getEventsByNonexistantDescendant() throws Exception {
        Connection conn = db.openConnection();
        EventDao eventDao = new EventDao(conn);

        //  Database starts out empty, so we go ahead
        try{
            Event[] events = eventDao.getEventsByDescendant(event.descendant);
            assert( events.length == 0 );

        } catch ( Exception ex ){
            //  If any exceptions are thrown, fails
            fail();
        }


    }

    @Test
    public void getEventByEventID() throws Exception {
        Connection conn = db.openConnection();
        EventDao eventDao = new EventDao(conn);

        //  Add an event
        eventDao.add(event);

        //  Query for same event
        Event actual = eventDao.getEventByEventID(event.eventID);

        //  Assert the eventID's match
        assertEquals(event.eventID, actual.eventID);

    }

    @Test
    public void getEventByNonexistantID() throws Exception {
        Connection conn = db.openConnection();
        EventDao eventDao = new EventDao(conn);

        //  Database begins as empty, so we just go
        try {
            //  Should return a null event object.
            Event actual = eventDao.getEventByEventID(event.eventID);
            assertNull(actual);
        } catch (Exception ex){
            //  If any exception is thrown, fails
            fail();
        }
    }
}